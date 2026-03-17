#!/bin/bash
##############################################################################
# Pre-run executable: allow_safari_downloads.sh
#
# Upload:
#   US data center:
#   curl -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY" \
#        -F "payload=@allow_safari_downloads.sh" \
#        -F "name=allow_safari_downloads.sh" \
#        "https://api.us-west-1.saucelabs.com/v1/storage/upload"
#
#   EU data center:
#   curl -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY" \
#        -F "payload=@allow_safari_downloads.sh" \
#        -F "name=allow_safari_downloads.sh" \
#        "https://api.eu-central-1.saucelabs.com/v1/storage/upload"
#
# sauce:options:
#   prerun.executable = "storage:filename=allow_safari_downloads.sh"
#   prerun.background  = false
#   prerun.timeout     = 60
##############################################################################

set -x

if [ -z "$HOME" ]; then
    export HOME="/Users/$(whoami)"
    echo "HOME was empty – corrected to: $HOME"
fi

ts() { date '+%H:%M:%S'; }

# Global watchdog – script must finish in under 60 s.
(
    sleep 60
    echo "WATCHDOG [$(ts)]: killing pid $$"
    kill -TERM $$ 2>/dev/null
    sleep 3
    kill -KILL $$ 2>/dev/null
) &
WATCHDOG_PID=$!
trap 'kill "$WATCHDOG_PID" 2>/dev/null; wait "$WATCHDOG_PID" 2>/dev/null' EXIT

# ---------------------------------------------------------------------------
# Insert the global "allow downloads from all sites" row into Safari's
# per-site preferences database.
#
# Observed by diffing PerSitePreferences.db before and after manually enabling
# "Allow Downloads from All Sites" in Safari Preferences › Websites › Downloads.
# The single change is one row in default_preferences:
#   preference='PerSitePreferencesDownloads', default_value=0
# ---------------------------------------------------------------------------

DB="$HOME/Library/Safari/PerSitePreferences.db"

if [ ! -f "$DB" ]; then
    echo "FAIL: database not found: $DB"
    exit 1
fi

echo "DB before:"
sqlite3 "$DB" .dump

sqlite3 "$DB" \
    "INSERT OR REPLACE INTO default_preferences(preference, default_value, sync_data)
     VALUES('PerSitePreferencesDownloads', 0, NULL);"
RC=$?

if [ $RC -eq 0 ]; then
    echo "OK ($RC): row inserted"
else
    echo "FAIL ($RC): sqlite3 returned an error"
    exit $RC
fi

echo "DB after:"
sqlite3 "$DB" .dump

echo "SUCCESS [$(ts)] – exiting 0"
exit 0
