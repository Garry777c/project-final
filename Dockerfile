FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} jira-1.0.jar
COPY resources ./resources
COPY target ./target

ENV spring_datasourse_url=jdbc:postgresql://localhost:5432/jira
ENV spring_datasourse_username=jira
ENV spring_datasourse_password=JiraRush

ENV GITHUB_CLIENT_ID=3d0d8738e65881fff266
ENV GITHUB_CLIENT_SECRET=0f97031ce6178b7dfb67a6af587f37e222a16120

ENV GOOGLE_CLIENT_ID=329113642700-f8if6pu68j2repq3ef6umd5jgiliup60.apps.googleusercontent.com
ENV GOOGLE_CLIENT_SECRET=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

ENV GITLAB_CLIENT_ID=b8520a3266089063c0d8261cce36971defa513f5ffd9f9b7a3d16728fc83a494
ENV GITLAB_CLIENT_SECRET=e72c65320cf9d6495984a37b0f9cc03ec46be0bb6f071feaebbfe75168117004

ENV jira4jr_username=jira4jr@gmail.com
ENV jira4jr_password=zdfzsrqvgimldzyj
ENV jira4jr_host=smtp.gmail.com
ENV jira4jr_port=587

ENV gitlab_authorization-uri=https://gitlab.com/oauth/authorize
ENV gitlab_token-uri=https://gitlab.com/oauth/token
ENV gitlab_user-info-uri=https://gitlab.com/api/v4/user
ENV gitlab_user-name-attribute=email

EXPOSE 8083
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "./target/jira-1.0.jar"]