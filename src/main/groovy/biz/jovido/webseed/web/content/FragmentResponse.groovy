package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Fragment
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
class FragmentResponse {

    static class Serializer extends JsonSerializer<FragmentResponse> {

        @Override
        void serialize(FragmentResponse response, JsonGenerator generator, SerializerProvider serializers) throws IOException, JsonProcessingException {
            def fragment = response.fragment
            generator.writeStartObject()
            generator.writeStringField('id', fragment.id)
            generator.writeStringField('revisionId', fragment.revisionId)

            generator.writeEndObject()
        }
    }

    final Fragment fragment

    FragmentResponse(Fragment fragment) {
        this.fragment = fragment
    }
}
