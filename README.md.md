<p>#Qualibrate Java API<br>
This project is implementation of python based qualibrate APIs. This project covers following 3 entities.</p>
<ul>
<li>User Entity</li>
<li>Project Entity</li>
<li>Files Entity (File Upload)</li>
</ul>
<p>These REST APIs are written in Java 8 using Spring Boot. For API documentation Swagger is used. Test cases are implemented using Junit and are executed with every build. For testing purpose H2DB is used with ‘test’ profile.</p>
<h3 id="technologyapplication-stack">Technology/Application Stack</h3>
<p>-Language : Java 8</p>
<ul>
<li>Frameworks :
<ul>
<li>Spring Boot (4.2.4.RELEASE) : To implement web services.</li>
<li>Swagger (2.4.0) : For API documentation purpose.</li>
<li>lombok (1.18.8) : To keep code neat and clean. To generate access methods using java agent.</li>
</ul>
</li>
<li>Gradle plugins:
<ul>
<li>Checkstyle: Plugin is used to maintain code formatting and validate     syntax such as CamelCase, spaces.</li>
<li>Docker: Plugin is used to create docker image of spring boot application.</li>
<li>FindBugs: Plugin is used to detect errors in code such as memory leaks, null references, possible null pointer exceptions etc.</li>
<li>Zip: Plugin is used to build beanstalk zip file/build packages.</li>
</ul>
</li>
</ul>
<h2 id="high-level-design-approach">High Level Design Approach</h2>
<p>APIs are developed by considering following requirements.</p>
<ul>
<li><strong>Lines of code and template based design</strong> :  Reflection and java 8 streaming apis are heavily used in code to generalize things and to reduce lines of code. e.g. EntityToDTO converter is developed using java reflection and is generalized to convert entity based on type. Template based design approach is used to generalize common behavior across application entities.</li>
<li><strong>Security</strong>: Api security is must. Added basic authentication support in API and configured firewall in application. To maintain security one can configure firewall rules to restrict URL access by configuring URL pattern rules in firewall. Role based access is maintained using Spring boot role based authentication and authorization mechanism at method level.</li>
<li><strong>CORS</strong> :  CORS (Cross Origin and Resource Sharing) Origin and Resource Sharing feature is configured for globally (all controllers and methods). To configure domain please specify allowed.origin environment variable while booting application. Please refer to <strong>Environment Variables</strong> section for more details.</li>
<li><strong>CICD</strong> : Travis is used to build application and deploy it on beanstalk.</li>
<li><strong>Application Monitoring</strong> : Portainer is used to monitor docker image.</li>
<li><strong>Large File Upload Support</strong>: Multipart upload is enabled and Java Input stream is used to read data in chunks in order to support large file upload. File upload size can be managed using “file-size-threshold” in KB (to manage multiparts) and maz-file-size (in MB) and max-request-size (in MB).</li>
<li><strong>Error code standardization</strong> : API responses and error codes are standardized across application by considering various possible failure scenarios. Please refer to <strong>Error Code details</strong> section for more details.</li>
</ul>
<h2 id="environment-variables">Environment Variables</h2>
<ul>
<li>SPRING_DATASOURCE_USERNAME : Database user name</li>
<li>SPRING_DATASOURCE_PASSWORD : Database password</li>
<li>FILE_UPLOADDIR : Upload file directory</li>
<li>ALLOWED_ORIGINS: CORS origins (<a href="http://localhost:8080">http://localhost:8080</a>)</li>
<li>SPRING_PROFILES_ACTIVE : (Possible values ‘local’, ‘production’, ‘test’, ‘aws’</li>
<li>SPRING_SERVLET_MULTIPART_ENABLED : true/false</li>
<li>SPRING_SERVLET_FILE-SIZE-THRESHOLD : 2KB</li>
<li>SPRING_SERVLET_MAX-FILE-SIZE : 200MB</li>
<li>SPRING_SERVLET_MAX-REQUEST-SIZE : 250MB</li>
</ul>
<h2 id="error-codes-and-api-responses">Error Codes and API responses</h2>
<ul>
<li>ISE_001 : To indicate internal server error /  unhandeled exception scenarios.</li>
<li>VE0001 : To indicate validation errors with request.</li>
<li>NF0001 : To indicate Resource Not Found Exception.</li>
<li>FNS_0001 : Format not supported</li>
<li>Entity Specific error codes
<ul>
<li>User API :
<ul>
<li>USRE_001 : User Already Exists</li>
<li>USRE_002: User Entity Not Found</li>
<li>UEC_003 : Unauthorized access</li>
</ul>
</li>
<li>Project Entity
<ul>
<li>PRJ_001 : Project already exists.</li>
<li>PRJ_002 : Project details not found.</li>
</ul>
</li>
</ul>
</li>
</ul>
<h2 id="prerequisites">Prerequisites</h2>
<ul>
<li>Java 8 SDK</li>
<li>MySQL 5.7.21 running on port 3306 with an existing DB schema as “qualibrate”</li>
<li>Gradle 5.5.1</li>
</ul>
<h2 id="build-information">Build Information</h2>
<p>Gradle (5.5.1) is used to build and package application. It also runs checkstyle, findbugs and runs test cases in every build.</p>
<p>Run Gradle clean build task to generate beanstalk zip, docker image and spring boot jar.<br>
Below are more details about tasks.</p>
<h2 id="build-steps">Build Steps</h2>
<p>Gradle build script has multiple tasks. Please refer to Build tasks section for more details.<br>
To run build on local system and deploy it on elastic beanstalk or docker follow below instructions.</p>
<h3 id="build-on-local-system">Build on local system</h3>
<ul>
<li>clone this repository</li>
<li>make sure you have JDK 8 installed</li>
<li>in root of project run below command
<ul>
<li>gradlew clean build<br>
Above command generates docker image, elastic beanstalk deployment package and spring boot jar.</li>
</ul>
</li>
</ul>
<h3 id="deployment-details">Deployment Details</h3>
<h4 id="docker-deployment-steps">Docker Deployment Steps</h4>
<ul>
<li><strong>Run using docker image</strong></li>
</ul>
<p>Above build process generates docker image on your system. use following command to run docker image in container.</p>
<ol>
<li>Run image on your local docker container.</li>
</ol>
<blockquote>
<p>docker container run -d -p 8080:9098 --name<br>
qualibrate-api-challange-instance-1 -v<br>
/var/run/docker.sock:/var/run/docker.sock {generated-image-id}</p>
</blockquote>
<p>Note: On Windows docker is not able to detect jar file location. To fix this issue replace following content in localDockerfile file.</p>
<blockquote>
<p>Replace<br>
“COPY <em>.jar /dockerapi/qualibrate-api-challange-1.0.0.jar"<br>
with<br>
"COPY build/libs/quali</em>.jar /opt/app/server.jar”</p>
</blockquote>
<ul>
<li><strong>Run application locally</strong> (java -jar)</li>
</ul>
<pre><code>java -jar .\build\libs\qualibrate-api-challange-1.0.0.jar --spring.profiles.active=local --spring.datasource.username={your db username} --spring.datasource.password={your db password}

</code></pre>
<h2 id="gradle-build-task-details">Gradle Build Task Details</h2>
<pre><code>Application tasks
-----------------
bootRun - Runs this project as a Spring Boot application.

Build tasks
-----------
assemble - Assembles the outputs of this project.
bootJar - Assembles an executable jar archive containing the main classes and their dependencies.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes.
testClasses - Assembles test classes.
**buildDocker**: build docker image
**beanstalkZip:** Build beanstalk image.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
asciidoctor - Converts AsciiDoc files and copies the output files and related resources to the build directory.
javadoc - Generates Javadoc API documentation for the main source code.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'qualibrate-api-challange'.
components - Displays the components produced by root project 'qualibrate-api-challange'. [incubating]
dependencies - Displays all dependencies declared in root project 'qualibrate-api-challange'.
dependencyInsight - Displays the insight into a specific dependency in root project 'qualibrate-api-challange'.
dependentComponents - Displays the dependent components of components in root project 'qualibrate-api-challange'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project 'qualibrate-api-challange'. [incubating]
projects - Displays the sub-projects of root project 'qualibrate-api-challange'.
properties - Displays the properties of root project 'qualibrate-api-challange'.
tasks - Displays the tasks runnable from root project 'qualibrate-api-challange'.

Reporting tasks
---------------
projectReport - Generates a report about your project.

Verification tasks
------------------
check - Runs all checks.
jacocoTestCoverageVerification - Verifies code coverage metrics based on specified rules for the test task.
jacocoTestReport - Generates code coverage report for the test task.
test - Runs the unit tests.
Build : Following tasks are configured in default build process.

## Sequence Diagrams

Below is a Sequence diagram for API usage.

```mermaid
sequenceDiagram
Qualibrate Admin-&gt;&gt; User API: Create User
Qualibrate Admin--&gt;&gt;Project API: Create Project.
Qualibrate Admin--&gt;&gt;User API: Assign Project to user.
User--x Project API: Get assigned project details!
User-x File API: Upload files related to project
</code></pre>
<p>API flow chart:</p>
<div class="mermaid"><svg xmlns="http://www.w3.org/2000/svg" id="mermaid-svg-zWK77YqoLtWCNuK6" width="100%" style="max-width: 1076.8500213623047px;" viewBox="0 0 1076.8500213623047 97.71665954589844"><g transform="translate(-12, -12)"><g class="output"><g class="clusters"></g><g class="edgePaths"><g class="edgePath" style="opacity: 1;"><path class="path" d="M186.03334045410156,72.53749465942383L264.52500915527344,72.53749465942383L343.0166778564453,72.53749465942383" marker-end="url(#arrowhead112)" style="fill:none"></path><defs><marker id="arrowhead112" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1px; stroke-dasharray: 1px, 0px;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M437.5666809082031,72.53749465942383L462.5666809082031,72.53749465942383L487.5666809082031,72.53749465942383" marker-end="url(#arrowhead113)" style="fill:none"></path><defs><marker id="arrowhead113" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1px; stroke-dasharray: 1px, 0px;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M590.2333526611328,52.914824504719206L615.2333526611328,43.35832977294922L640.2333526611328,43.35832977294922" marker-end="url(#arrowhead114)" style="fill:none"></path><defs><marker id="arrowhead114" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1px; stroke-dasharray: 1px, 0px;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M803.0666809082031,43.35832977294922L828.0666809082031,43.35832977294922L853.0666809082031,43.35832977294922" marker-end="url(#arrowhead115)" style="fill:none"></path><defs><marker id="arrowhead115" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1px; stroke-dasharray: 1px, 0px;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M943.6166839599609,43.35832977294922L968.6166839599609,43.35832977294922L993.6166839599609,53.98955300150282" marker-end="url(#arrowhead116)" style="fill:none"></path><defs><marker id="arrowhead116" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1px; stroke-dasharray: 1px, 0px;"></path></marker></defs></g><g class="edgePath" style="opacity: 1;"><path class="path" d="M993.6166839599609,91.08543631734483L968.6166839599609,101.71665954589844L898.341682434082,101.71665954589844L828.0666809082031,101.71665954589844L721.650016784668,101.71665954589844L615.2333526611328,101.71665954589844L590.2333526611328,92.16016481412845" marker-end="url(#arrowhead117)" style="fill:none"></path><defs><marker id="arrowhead117" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6" orient="auto"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowheadPath" style="stroke-width: 1px; stroke-dasharray: 1px, 0px;"></path></marker></defs></g></g><g class="edgeLabels"><g class="edgeLabel" style="opacity: 1;" transform="translate(264.52500915527344,72.53749465942383)"><g transform="translate(-53.491668701171875,-13.358329772949219)" class="label"><foreignObject width="106.98333740234375" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel">security checks</span></div></foreignObject></g></g><g class="edgeLabel" style="opacity: 1;" transform=""><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" style="opacity: 1;" transform=""><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" style="opacity: 1;" transform=""><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" style="opacity: 1;" transform=""><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g><g class="edgeLabel" style="opacity: 1;" transform=""><g transform="translate(0,0)" class="label"><foreignObject width="0" height="0"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;"><span class="edgeLabel"></span></div></foreignObject></g></g></g><g class="nodes"><g class="node" style="opacity: 1;" id="A" transform="translate(103.01667022705078,72.53749465942383)"><rect rx="0" ry="0" x="-83.01667022705078" y="-23.35832977294922" width="166.03334045410156" height="46.71665954589844"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-73.01667022705078,-13.358329772949219)"><foreignObject width="146.03334045410156" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">Spring Security Filter</div></foreignObject></g></g></g><g class="node" style="opacity: 1;" id="B" transform="translate(390.2916793823242,72.53749465942383)"><rect rx="5" ry="5" x="-47.275001525878906" y="-23.35832977294922" width="94.55000305175781" height="46.71665954589844"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-37.275001525878906,-13.358329772949219)"><foreignObject width="74.55000305175781" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">Basic Auth</div></foreignObject></g></g></g><g class="node" style="opacity: 1;" id="C" transform="translate(538.900016784668,72.53749465942383)"><rect rx="5" ry="5" x="-51.333335876464844" y="-23.35832977294922" width="102.66667175292969" height="46.71665954589844"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-41.333335876464844,-13.358329772949219)"><foreignObject width="82.66667175292969" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">CORS Filter</div></foreignObject></g></g></g><g class="node" style="opacity: 1;" id="D" transform="translate(721.650016784668,43.35832977294922)"><rect rx="5" ry="5" x="-81.41666412353516" y="-23.35832977294922" width="162.8333282470703" height="46.71665954589844"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-71.41666412353516,-13.358329772949219)"><foreignObject width="142.8333282470703" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">Controller Mappings</div></foreignObject></g></g></g><g class="node" style="opacity: 1;" id="E" transform="translate(898.341682434082,43.35832977294922)"><rect rx="5" ry="5" x="-45.275001525878906" y="-23.35832977294922" width="90.55000305175781" height="46.71665954589844"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-35.275001525878906,-13.358329772949219)"><foreignObject width="70.55000305175781" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">Controller</div></foreignObject></g></g></g><g class="node" style="opacity: 1;" id="F" transform="translate(1037.2333526611328,72.53749465942383)"><rect rx="5" ry="5" x="-43.616668701171875" y="-23.35832977294922" width="87.23333740234375" height="46.71665954589844"></rect><g class="label" transform="translate(0,0)"><g transform="translate(-33.616668701171875,-13.358329772949219)"><foreignObject width="67.23333740234375" height="26.716659545898438"><div xmlns="http://www.w3.org/1999/xhtml" style="display: inline-block; white-space: nowrap;">Response</div></foreignObject></g></g></g></g></g></g></svg></div>

