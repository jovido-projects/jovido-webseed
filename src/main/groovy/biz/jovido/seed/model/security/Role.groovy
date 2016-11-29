package biz.jovido.seed.model.security

import org.springframework.security.core.GrantedAuthority

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'role')
@Entity
class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String authority

    @OneToMany
    @JoinTable(name = 'role_permission',
            joinColumns = @JoinColumn(name = 'role_id', referencedColumnName = 'id'),
            inverseJoinColumns = @JoinColumn(name = 'permission_id', referencedColumnName = 'id'))
    final Set<Permission> permissions = new LinkedHashSet()

    Set<Permission> getPermissions() {
        Collections.unmodifiableSet(permissions)
    }

    boolean addPermission(Permission permission) {
        permissions.add(permission)
    }

    boolean removePermission(Permission permission) {
        permissions.remove(permission)
    }
}
