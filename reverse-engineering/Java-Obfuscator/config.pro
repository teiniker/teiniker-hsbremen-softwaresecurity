-injars       build/ConsoleLogin-0.0.1-SNAPSHOT.jar
-outjars      build/ConsoleLogin-0.0.1.jar
-libraryjars  <java.home>/lib/rt.jar
-printmapping obfuscation.map

-optimizationpasses 3
-overloadaggressively
-repackageclasses ''
-allowaccessmodification

-keep public class org.se.lab.Main 
{
    public static void main(java.lang.String[]);
}