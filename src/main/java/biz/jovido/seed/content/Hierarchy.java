package biz.jovido.seed.content;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class Hierarchy {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "hierarchy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "locale")
    private final Map<Locale, Branch> branches = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Locale, Branch> getBranches() {
        return Collections.unmodifiableMap(branches);
    }

    public Branch getBranch(Locale locale) {
        return branches.get(locale);
    }

    public Branch putBranch(Branch branch) {
        Assert.notNull(branch);
        Locale locale = branch.getLocale();
        Assert.notNull(locale);
        Branch replaced = branches.put(locale, branch);
        if (replaced != null) {
            branch.setHierarchy(null);
        }

        branch.setHierarchy(this);

        return replaced;
    }
//    public Branch setBranch(Locale locale, Branch branch) {
//        Branch replaced = branches.put(locale, branch);
//        if (replaced != null) {
//            replaced.setHierarchy(null);
//            replaced.setLocale(null);
//        }
//
//        if (branch != null) {
//            branch.setHierarchy(this);
//            branch.setLocale(locale);
//        }
//
//        return replaced;
//    }
}
