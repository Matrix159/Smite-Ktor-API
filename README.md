# Smite Ktor API

A Ktor server that provides access to Hi-Rez's Smite API.

## Setup
* Ensure a Java SDK version is installed that's compatible with Kotlin version `1.9.22`.
* Create environment variables of API_ID and API_KEY. These can be retrieved by submitting an application form here: https://fs12.formsite.com/HiRez/form48/secure_index.html

## Tips
* Visit the root of the API at `/` to see the available endpoints available. 
* This API handles the Smite API session tokens for you.
* There is a general rate limiter built in.
* There is a 300 max-age on all requests for caching.
