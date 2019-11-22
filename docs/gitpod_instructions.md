# Gitpod Setup Instructions

1. Download the [gitpod browser extension](https://www.gitpod.io/docs/20_browser_extension/)
2. Refresh your browser to see this button now appear in GitHub:![Gitpod Button](gitpod_button.png)
3. Click the button and follow the instructions to allow gitpod access to GitHub.
4. After the session launches, navigate to the terminal and use the following commands to [export your Sauce Labs credentials:](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials)
```
eval $(gp env -e SAUCE_USERNAME=******)
eval $(gp env -e SAUCE_ACCESS_KEY=******)
```

> If you start a new terminal in gitpod, you have to use the following command to re-apply environment variables:
> ```
> eval $(gp env -e)
> ```

For more information consult the [gitpod documentation](https://www.gitpod.io/docs/47_environment_variables/)
