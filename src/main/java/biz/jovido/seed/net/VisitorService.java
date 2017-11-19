package biz.jovido.seed.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@Service
public class VisitorService {

    private static final Logger LOG = LoggerFactory.getLogger(VisitorService.class);

    private static final String DEFAULT_VISITOR_COOKIE_NAME = "VISITOR";

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private HostService hostService;

    public Visitor getOrCreateVisitor(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            UUID uuid = Stream.of(cookies)
                    .filter(cookie -> DEFAULT_VISITOR_COOKIE_NAME.equals(cookie.getName()))
                    .map(cookie -> UUID.fromString(cookie.getValue()))
                    .findFirst()
                    .orElse(null);

            if (uuid == null) {
                uuid = UUID.randomUUID();
                Cookie cookie = new Cookie(DEFAULT_VISITOR_COOKIE_NAME, uuid.toString());
                cookie.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);

                LOG.debug("Set new visitor cookie [{}]", uuid);
            }

            Visitor visitor = visitorRepository.findByUuid(uuid);
            if (visitor == null) {
                visitor = new Visitor();
                visitor.setUuid(uuid);
                return visitorRepository.saveAndFlush(visitor);
            } else {
//                auditingHandler.markModified(visitor);
                return visitorRepository.save(visitor);
            }
        }

        return null;
    }

    public void toVisit(HttpServletRequest request) {
        Visit visit = new Visit();

        Visitor visitor = (Visitor) request.getAttribute(Visitor.class.getName());
        visit.setVisitor(visitor);

        Host host = (Host) request.getAttribute(Host.class.getName());
        visit.setHost(host);

        visit.setPointInTime(new Date());
        visit.setIpAddress(request.getRemoteAddr());
        visit.setUserAgent(request.getHeader("User-Agent"));

        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.hasLength(queryString)) {
            uri = String.format("%s?%s", uri, queryString);
        }
        visit.setUrl(uri);

        Visit saved = visitRepository.save(visit);
        Assert.notNull(saved);
    }
}
