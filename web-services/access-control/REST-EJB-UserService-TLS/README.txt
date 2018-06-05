

How to configure Wildfly's HTTPS connection?
-------------------------------------------------------------------------------
*) Create a Keystore

$ cd JBOSS_HOME/standalone/configuration
$ keytool -genkeypair -keystore wildfly.keystore -storepass student -keypass student -keyalg RSA -alias wildfly -dname "cn=ims,o=fhj,c=at" 
 
*) Configure Wildfly (standalone.xml):

        <management>
        <security-realms>
			...
            
            <!-- SSL/TLS Configuration (BEGIN) --> 
			<security-realm name="CertificateRealm">
				<server-identities>
					<ssl>
						<keystore path="wildfly.keystore" relative-to="jboss.server.config.dir" keystore-password="student" />
					</ssl>
				</server-identities>
			</security-realm>
			<!-- SSL/TLS Configuration (END) -->

        </security-realms>

	
       <subsystem xmlns="urn:jboss:domain:undertow:2.0">
            <buffer-cache name="default"/>
            <server name="default-server">
                <http-listener name="default" socket-binding="http" redirect-socket="https"/>
				
				<!-- SSL/TLS Configuration (BEGIN) -->  
				<https-listener name="https" socket-binding="https" security-realm="CertificateRealm"/>	
                <!-- SSL/TLS Configuration (END) -->
                
                <host name="default-host" alias="localhost">
                    <location name="/" handler="welcome-content"/>
                    <filter-ref name="server-header"/>
                    <filter-ref name="x-powered-by-header"/>
                </host>
            </server>
			...
        </subsystem>

*) Restart Wildfly
	=> Undertow HTTPS listener https listening on localhost/127.0.0.1:8443
	
	
How to configure a WS application to use TLS?
-------------------------------------------------------------------------------

src/main/webapp/WEB-INF/web.xml

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>All resources</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
		

How to access the REST resource?
-------------------------------------------------------------------------------

URL http://localhost:8080/REST-EJB-UserService-TLS/v1/users

=> https://localhost:8443/REST-EJB-UserService-TLS/v1/users


findAll()
-------------------------------------------------------------------------------
GET /REST-EJB-UserService-TLS/v1/users HTTP/1.1
Accept-Encoding: gzip,deflate
Host: localhost:8443
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
Connection: close


HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: application/xml
Content-Length: 553
Date: Fri, 07 Oct 2016 10:49:59 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<collection>
	<user id="1"><username>homer</username><password>ijD8qepbRnIsva0kx0cKRCcYysg=</password></user>
	<user id="2"><username>marge</username><password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password></user>
	<user id="3"><username>bart</username><password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password></user>
	<user id="4"><username>lisa</username><password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password></user>
	<user id="5"><username>burns</username><password>YDYg2ELvjAWIllkU7wbECt/lr6w=</password></user>
</collection>


findById()
-------------------------------------------------------------------------------
GET /REST-EJB-UserService-TLS/v1/users/3 HTTP/1.1
Accept-Encoding: gzip,deflate
Host: localhost:8443
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
Connection: close

HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: application/xml
Content-Length: 149
Date: Fri, 07 Oct 2016 10:52:05 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<user id="3">
	<username>bart</username><password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>
</user>



delete()
-------------------------------------------------------------------------------
DELETE /REST-EJB-UserService-TLS/v1/users/3 HTTP/1.1
Accept-Encoding: gzip,deflate
Content-Type: application/json
Content-Length: 0
Host: localhost:8443
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
Connection: close

HTTP/1.1 204 No Content
X-Powered-By: Undertow/1
Server: WildFly/10
Date: Fri, 07 Oct 2016 10:53:56 GMT


insert()
-------------------------------------------------------------------------------
POST /REST-EJB-UserService-TLS/v1/users HTTP/1.1
Accept-Encoding: gzip,deflate
Content-Type: application/xml
Content-Length: 99
Host: localhost:8443
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
Connection: close

<user id="7">
	<username>burns</username><password>YDYg2ELvjAWIllkU7wbECt/lr6w=</password>
</user>


HTTP/1.1 201 Created
Connection: close
X-Powered-By: Undertow/1
Server: WildFly/10
Location: http://localhost:8080/REST-EJB-UserService-TLS/users/7
Content-Length: 0
Date: Fri, 07 Oct 2016 10:58:06 GMT


update()
-------------------------------------------------------------------------------
PUT /REST-EJB-UserService-TLS/v1/users/3 HTTP/1.1
Accept-Encoding: gzip,deflate
Content-Type: application/xml
Content-Length: 98
Host: localhost:8443
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
Connection: close

<user id="3">
	<username>bart</username><password>oFgVIpVXe0VklV9OTmZyrnIb4yk=</password>
</user>


HTTP/1.1 204 No Content
X-Powered-By: Undertow/1
Server: WildFly/10
Date: Fri, 07 Oct 2016 11:00:51 GMT


How to use server-driven Media Type Negotiation?
-------------------------------------------------------------------------------
In SoapUI go to the Headers tab and add a new one [+]
Type in "Accept" for the name and set the value to "application/json"
Run the request again.

GET /REST-EJB-UserService-TLS/v1/users HTTP/1.1
Accept-Encoding: gzip,deflate
Accept: application/json
Host: localhost:8443
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
Connection: close


HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: application/json
Content-Length: 279
Date: Thu, 20 Oct 2016 13:19:25 GMT

[
	{"id":1,"username":"homer","password":"ijD8qepbRnIsva0kx0cKRCcYysg="},
	{"id":2,"username":"marge","password":"xCSuPDv2U6I5jEO1wqvEQ/jPYhY="},
	{"id":3,"username":"bart","password":"Ls4jKY8G2ftFdy/bHTgIaRjID0Q="},
	{"id":4,"username":"lisa","password":"xO0U4gIN1F7bV7X7ovQN2TlSUF4="}
]
