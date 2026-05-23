package PedroM_Guerra.controle_aso.mapper;

import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.model.Aso;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    // Criamos a configuração de mapeamento específica
    private static BeanMappingBuilder asoMapping = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(Aso.class, AsoDTO.class)
                    .fields("funcionario.id", "funcionarioId");
        }
    };

    // Construímos o mapper passando a configuração
    private static Mapper mapper = DozerBeanMapperBuilder.create()
            .withMappingBuilder(asoMapping)
            .build();

    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){

        List<D> destinationObjects = new ArrayList<D>();
        for (Object o : origin){
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
