FROM tomcat:8.0-jre8-alpine

RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]
ADD ./target/REST-data-service-1.0.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]