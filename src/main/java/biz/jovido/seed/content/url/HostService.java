package biz.jovido.seed.content.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;

    public Host getHost(Long id) {
        return hostRepository.findOne(id);
    }

    public Host getHost(String name) {
        return hostRepository.findByName(name);
    }

    public Host saveHost(Host host) {
        return hostRepository.saveAndFlush(host);
    }

    public Host getOrCreateHost(String name) {
        Host host = getHost(name);
        if (host == null) {
            host = new Host();
            host.setName(name);
            host = saveHost(host);
        }

        return host;
    }

    public List<Host> getAllHosts() {
        return hostRepository.findAll();
    }
}
