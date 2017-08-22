package biz.jovido.seed.security.model;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class UserListing {

    private Page<User> page;

    public Page<User> getPage() {
        return page;
    }

    public void setPage(Page<User> page) {
        this.page = page;
    }

    public List<User> getUsers() {
        return page.getContent();
    }
}
