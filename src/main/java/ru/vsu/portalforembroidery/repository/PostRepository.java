package ru.vsu.portalforembroidery.repository;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vsu.portalforembroidery.model.entity.PostEntity;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer>, JpaSpecificationExecutor<PostEntity> {

    @NonNull
    @Override
    Page<PostEntity> findAll(Specification<PostEntity> specification, @NonNull Pageable pageable);

    static Specification<PostEntity> hasDesignerIdAndTagName(final int designerId, final String tagName) {
        return (root, query, criteriaBuilder) -> {
            System.out.println(root.join("designer", JoinType.LEFT).get("id").toString());
            final Predicate designerIdPredicate =
                    criteriaBuilder.equal(root.join("designer", JoinType.LEFT).get("id"), designerId);
            final Predicate tagNamePredicate = Strings.isBlank(tagName) ? null :
                    criteriaBuilder.like(root.join("design", JoinType.LEFT).join("tags").get("title"), "%" + tagName + "%");

            final Predicate[] objects = Stream.of(designerIdPredicate, tagNamePredicate).filter(Objects::nonNull).toArray(Predicate[]::new);
            return criteriaBuilder.and(objects);
        };
    }

    static Specification<PostEntity> hasTagName(final String tagName) {
        return (root, query, criteriaBuilder) -> {
            final Predicate tagNamePredicate = Strings.isBlank(tagName) ? null :
                    criteriaBuilder.like(root.join("design", JoinType.LEFT).join("tags").get("title"), "%" + tagName + "%");

            final Predicate[] objects = Stream.of(tagNamePredicate).filter(Objects::nonNull).toArray(Predicate[]::new);
            return criteriaBuilder.and(objects);
        };
    }

}
