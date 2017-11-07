package biz.jovido.seed.net;

import com.google.common.net.InternetDomainName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;

    public String getHostName(String url) {
        InternetDomainName publicDomain = InternetDomainName.from(url);
        InternetDomainName topPrivateDomain = publicDomain.topPrivateDomain();
        return topPrivateDomain.toString();
    }

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

    public String getHostName(HttpServletRequest request) {
        String hostName = request.getHeader("X-Forwarded-Host");
        if (StringUtils.isEmpty(hostName)) {
            hostName = request.getServerName();
        }

        if (!"localhost".equals(hostName)) {
            hostName = getHostName(hostName);
        }

        return hostName;
    }

    public Host getHost(HttpServletRequest request) {
        String hostName = getHostName(request);
        return getHost(hostName);
    }

    public List<Host> getAllHosts() {
        return hostRepository.findAll();
    }
}
