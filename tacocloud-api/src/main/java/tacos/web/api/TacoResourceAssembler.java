package tacos.web.api;

import org.springframework.stereotype.Component;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import tacos.Taco;

@Component
public class TacoResourceAssembler implements RepresentationModelAssembler<Taco, EntityModel<TacoResources>> {

    @Override
    public EntityModel<TacoResources> toModel(Taco taco) {

        return EntityModel.of(new TacoResources(taco),
                linkTo(methodOn(DesignTacoController.class).tacoById(taco.getId())).withSelfRel()
        );
    }

    @Override
    public CollectionModel<EntityModel<TacoResources>> toCollectionModel(Iterable<? extends Taco> tacos) {
        return RepresentationModelAssembler.super.toCollectionModel(tacos).add(
                        linkTo(DesignTacoController.class).withSelfRel(),
                linkTo(methodOn(DesignTacoController.class).recentTacos()).withRel("recents")
        );
    }

}


//package tacos.web.api;
//
//import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
//
//import tacos.Taco;
//
//public class TacoResourceAssembler
//       extends ResourceAssemblerSupport<Taco, TacoResource> {
//
//  public TacoResourceAssembler() {
//    super(DesignTacoController.class, TacoResource.class);
//  }
//  
//  @Override
//  protected TacoResource instantiateResource(Taco taco) {
//    return new TacoResource(taco);
//  }
//
//  @Override
//  public TacoResource toResource(Taco taco) {
//    return createResourceWithId(taco.getId(), taco);
//  }
//
//}
