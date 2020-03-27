export START_TIME = $(shell date +%FT%T%Z)

clean_it:
	mvn clean

test_parallel:
	make -j windows_10_edge mac_sierra_chrome windows_7_ff windows_8_ie mac_mojave_safari

sauce_demo:
	make clean_it test_parallel

windows_10_edge:
	mvn clean test -Dplatform=$@

mac_sierra_chrome:
	mvn clean test -Dplatform=$@

windows_7_ff:
	mvn clean test -Dplatform=$@

windows_8_ie:
	mvn clean test -Dplatform=$@

mac_mojave_safari:
	mvn clean test -Dplatform=$@

