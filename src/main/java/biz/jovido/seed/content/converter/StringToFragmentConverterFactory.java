package biz.jovido.seed.content.converter;

import biz.jovido.seed.content.domain.Fragment;
import biz.jovido.seed.content.repository.FragmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

/**
 * @author Stephan Grundner
 */
@Component
public class StringToFragmentConverterFactory implements ConverterFactory<String, Fragment> {

    protected static class StringToFragmentConverter<T extends Fragment> implements Converter<String, T> {

        private final FragmentRepository fragmentRepository;

        @Override
        @SuppressWarnings("unchecked")
        public T convert(String source) {
            if (!StringUtils.isEmpty(source)) {
                Long id = NumberUtils.parseNumber(source, Long.class);
                return (T) fragmentRepository.findOne(id);
            }

            return null;
        }

        public StringToFragmentConverter(FragmentRepository fragmentRepository) {
            this.fragmentRepository = fragmentRepository;
        }
    }

    private final FragmentRepository fragmentRepository;

    public <T extends Fragment> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToFragmentConverter<>(fragmentRepository);
    }

    @Autowired
    public StringToFragmentConverterFactory(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    }
}
