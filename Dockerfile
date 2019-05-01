FROM java:8

EXPOSE 8080 5000

RUN export http_proxy=http://172.25.30.117:6060/
RUN export https_proxy=http://172.25.30.117:6060/

RUN set -e
RUN mkdir -p /opt/discover-post-inject-web
RUN mkdir -p /opt/discover-post-inject-web/config/

RUN cd /opt/discover-post-inject-web

RUN mkdir -p /log
RUN ln -s /log logs

WORKDIR /opt/discover-post-inject-web

RUN mv -f /etc/localtime /etc/localtime.bak
RUN ln -s /usr/share/zoneinfo/Africa/Lagos /etc/localtime

RUN set -e
RUN cd /opt/discover-post-inject-web/config/

RUN cd /opt/discover-post-inject-web/
COPY target/*.jar /opt/discover-post-inject-web/
CMD java -jar -Dspring.profiles.active=docker discover-post-inject-web-*.jar