FROM tomcat:8.5.15-jre8

LABEL maintainer="SaberSams"


ADD target/employee-reimbursement-system-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080:8080

CMD ["catalina.sh", "run"]