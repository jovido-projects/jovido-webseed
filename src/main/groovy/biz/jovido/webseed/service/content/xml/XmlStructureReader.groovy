package biz.jovido.webseed.service.content.xml

import biz.jovido.webseed.model.content.Field
import biz.jovido.webseed.model.content.FieldGroup
import biz.jovido.webseed.model.content.FragmentType
import biz.jovido.webseed.model.content.Structure
import biz.jovido.webseed.model.content.constraint.AlphanumericConstraint
import biz.jovido.webseed.service.content.StructureReader
import biz.jovido.webseed.service.content.StructureService
import org.apache.commons.digester3.AbstractObjectCreationFactory
import org.apache.commons.digester3.Digester
import org.apache.commons.digester3.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.xml.sax.Attributes

/**
 *
 * @author Stephan Grundner
 */
@Component
class XmlStructureReader implements StructureReader {

    protected final StructureService structureService

    @Autowired
    XmlStructureReader(StructureService structureService) {
        this.structureService = structureService
    }

    @Override
    Structure readStructure(Resource resource) {
        def digester = new Digester()
        digester.setValidating(false)

        "structure".with {
            digester.addObjectCreate(it, Structure)
            digester.addSetProperties(it)
            digester.addRule(it, new Rule() {
                @Override
                void begin(String namespace, String name, Attributes attributes) throws Exception {
                    def structure = digester.peek() as Structure
                    digester.push('structure', structure)
                }

                @Override
                void end(String namespace, String name) throws Exception {
                    digester.pop('structure')
                }
            })

            "$it/constraints".with {
                "$it/constraint".with {
                    digester.addFactoryCreate(it, new AbstractObjectCreationFactory<Object>() {
                        @Override
                        Object createObject(Attributes attributes) throws Exception {
                            def type = attributes.getValue('type')
                            structureService.createConstraint(type)
                        }
                    })
                    digester.addSetProperties(it)
                    digester.addSetNestedProperties(it)
                    digester.addSetNext(it, 'putConstraint')
                }


                "$it/alphanumericConstraint".with {
                    digester.addObjectCreate(it, AlphanumericConstraint)
                    digester.addSetProperties(it)
                    digester.addSetNestedProperties(it)
                    digester.addSetNext(it, 'putConstraint')
                }
            }

            "$it/fragmentTypes".with {
                "$it/fragmentType".with {
                    digester.addObjectCreate(it, FragmentType)
                    digester.addSetNext(it, 'putFragmentType')
                    digester.addSetProperties(it)

                    "$it/field".with {

                        digester.addObjectCreate(it, Field)
                        digester.addSetProperties(it,
                                ['name', 'constraint', 'group'] as String[],
                                ['name', null, null] as String[])
                        digester.addRule(it, new Rule() {
                            @Override
                            void begin(String namespace, String name, Attributes attributes) throws Exception {
                                def structure = digester.peek('structure') as Structure
                                def field = digester.peek() as Field
                                def constraintName = attributes.getValue('constraint')
                                def constraint = structure.getConstraint(constraintName)
                                field.constraint = constraint

                                def fragmentType = digester.peek(1) as FragmentType
                                def fieldGroupName = attributes.getValue('group')
                                if (!StringUtils.isEmpty(fieldGroupName)) {
                                    def fieldGroup = fragmentType.fieldGroups.get(fieldGroupName)
                                    if (fieldGroup == null) {
                                        fieldGroup = new FieldGroup()
                                        fieldGroup.name = fieldGroupName
                                        fieldGroup.fragmentType = fragmentType
                                    }
                                    fieldGroup.putField(field)
                                }

                                return
                            }
                        })
                        digester.addSetNext(it, 'putField', Field.name)
                    }
                }
            }

        }

        (Structure) digester.parse(resource.inputStream)
    }
}
