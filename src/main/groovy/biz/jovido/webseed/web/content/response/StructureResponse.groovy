package biz.jovido.webseed.web.content.response

import biz.jovido.webseed.model.content.Structure
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 *
 * @author Stephan Grundner
 */
@JsonSerialize(using = Serializer)
class StructureResponse {

    static class Serializer extends JsonSerializer<StructureResponse> {

        @Override
        void serialize(StructureResponse response, JsonGenerator generator, SerializerProvider serializers) throws IOException, JsonProcessingException {
            def structure = response.structure
            generator.writeStartObject()
            generator.writeStringField('revisionId', structure.revisionId)

            generator.writeArrayFieldStart('constraints')
            for (def constraint : structure.constraints.values()) {
                generator.writeStartObject()
                generator.writeStringField('name', constraint.name)
                generator.writeNumberField('minValues', constraint.minValues)
                generator.writeNumberField('maxValues', constraint.maxValues)
                generator.writeBooleanField('editable', constraint.editable)
                generator.writeBooleanField('updatable', constraint.updatable)
                generator.writeBooleanField('nullable', constraint.nullable)
                generator.writeEndObject()
            }
            generator.writeEndArray()

            generator.writeArrayFieldStart('fragmentTypes')
            for (def fragmentType : structure.fragmentTypes.values()) {
                generator.writeStartObject()
                generator.writeStringField('name', fragmentType.name)
                generator.writeArrayFieldStart('fields')
                for (def field : fragmentType.fields.values()) {
                    generator.writeStartObject()
                    generator.writeStringField('name', field.name)
                    generator.writeNumberField('ordinal', field.ordinal)
                    generator.writeStringField('constraint', field.constraint?.name)
                    generator.writeEndObject()
                }
                generator.writeEndArray()
                generator.writeEndObject()
            }
            generator.writeEndArray()

            generator.writeEndObject()
        }
    }

    final Structure structure

    StructureResponse(Structure structure) {
        this.structure = structure
    }
}
