# Gitpod Setup Instructions

1. Download the [gitpod browser extension](https://www.gitpod.io/docs/20_browser_extension/)
2. Refresh your browser to see this button now appear in GitHub:![Gitpod Button](gitpod_button.png)
3. Click the button and follow the instructions to allow gitpod access to GitHub.
4. After the session launches, navigate to the terminal and use the following commands to export your Sauce Labs credentials:
    ```
    eval $(gp env -e SAUCE_USERNAME=******)
    eval $(gp env -e SAUCE_ACCESS_KEY=******)
    ```
    > If you start a new bash terminal you'll have to refresh the session to apply the envrionment variables, to do this use the following command:
    > ```
    > eval $(gp env -e)
    > ``` 
5. Run the following command to ensure you've properly configured the test environment:
    ```
    mvn test -Dtest=Module2TestNGTest -pl on-boarding-scripts/testng
    ```
    > Refer to the documentation on how to retrieve your [Sauce Labs Credentials](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials)

For more information on how to use gitpod, consult the [gitpod documentation](https://www.gitpod.io/docs/47_environment_variables/)