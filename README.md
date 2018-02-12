# Changes to be made (reference Sauce Account)

```
	export SAUCE_USERNAME=your_username
	export SAUCE_ACCESS_KEY=your_access_key
```

# Running the tests
to run: `make run_all_in_parallel`

# Running the tests with a proxy - replace PORT with port number and localhost as necessary
```
mvn -Dhttp.proxyHost=localhost -Dhttp.proxyPort=PORT -Dhttps.proxyHost=localhost -Dhttps.proxyPort=PORT -Dhttps.proxyPort=PORT test
```
