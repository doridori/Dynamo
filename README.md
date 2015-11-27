[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Dynamo-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1805) [![Circle CI](https://circleci.com/gh/doridori/Dynamo.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/doridori/Dynamo) [![](https://img.shields.io/badge/AndroidWeekly-%23150-blue.svg)](http://androidweekly.net/issues/issue-150)

#DEPRECATED

This project is now deprecated and superseded by [Pilot](https://github.com/doridori/Pilot). 

# Dynamo

An extremely lightweight collection of classes for implementing a **state** based decoupled controller architecture for Android applications.

A major benefit of this is that it also keeps asynchronous code away from the Android lifecycle.

<img src="https://github.com/doridori/Dynamo/blob/master/gfx/DynamoDroid.png" alt="dynamo droid"/>

Motivation
----------

The majority of Android apps have no standard approach to architecture. Its _very_ rare to come across an apps codebase that says "I have used approach X listed here". Most apps I have found fall into the below camps

- The "`God-Activity`" (or `Fragment`) that does everything and has to balance asynchronous code with the Android lifecycle.
- A roll your own approach which still does not address all the issues associated with mixing asynchronous code and lifecycle events, which causes it to break down in some common use-cases.

There are some great libraries out there, its just that I have not come across one which is

1. Well documented 
2. Easy to jump into without a large ramp-up time
3. Solves the issues that come from the "default" approaches  (see the projects Wiki for more here) 

Similarly, there are many great blog posts at present which cover a subset of an MV* (i.e. MVP) approach but most of the ones I have read leave me with as many questions as I have answers, do not form a complete road-map and sometimes introduce unneeded complexities or limitations. This is my attempt to create something that is a:

1. Relatively accessible for those newer to the Android world 
2. Lightweight and easy to understand
3. Explains how it solves common issues and provides examples for these cases.
4. Easy to write automated tests against

Blog Posts
----------

The motivation for this lib is further outlined in two of my blog posts:

- [Android Architecture: MV?](http://doridori.github.io/Android-Architecture-MV%3F/)
- [Android Architecture: Introducing Dynamo](http://doridori.github.io/Android-Architecture-Dynamo/) 

Wiki
----

The Wiki for this repo contains documentation about how to use this library. [Check it out](https://github.com/doridori/Dynamo/wiki).

Questions / Feedback
--------------------

Admittedly, while trying to create a clear guide for people here I have probably created more confusion than you had to start with. If you have any questions, suggestions, abuse, lavish amounts of money or anything else you want to send my way please do. Use the Issues in this repo, twitter, the comments in my blog, the reddit posts, anything! At the end of the day I want to help you learn and learn myself so please feel free. Plus, if you think I have not explained something enough or have the wrong idea about something I want to hear it!

Download
--------

_jcenter/mvn coming after this short intermission_

For now can just grab the [latest release](https://github.com/doridori/Dynamo/releases) _i know i know_

Or to play with via Maven Local by cloning the repo and running `./gradlew pTML` and then adding `mavenLocal()` to your `resositories{...}` and `compile 'couk.doridori.dynamo:dynamo:1.0.0'`

License
--------

    Copyright 2014 Dorian Cussen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
