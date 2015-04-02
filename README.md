# Dynamo

An extremely lightweight collection of classes for implementing state based decoupled architecture for Android applications.

<img src="https://github.com/doridori/Dynamo/blob/master/gfx/DynamoDroid.png" alt="dynamo droid"/>

Motivation
----------

The majority of Android apps have no standard approach to architecture. Its _very_ rare to come across an apps codebase that says "I have used approach X listed here". Most apps I have found fall into the below camps

- The "`God-Activity`" (or `Fragment`) that does everything and has to balance asynchronous code with the Android lifecycle.
- A roll your own approach which still does not address all the issues associated with mixing asynchronous code and lifecycle events, which causes it to break down in some common use-cases.

This is certainly not to say that there are not well-architected approaches out there, its just that I have not come across one which is well documented _and_ easy to jump into _and_ solves the issues that come from the "default" approaches. 

There are many blog posts at present which cover a subset of an MV* approach but this is not enough IMHO for someone to link off to and say "this is what we are doing". This is my attempt to start something for which I personally can do just that. A simple approach which is:

1. Relatively accessible for those newer to the Android world 
2. Lightweight and easy to understand
3. Explains how it solves common issues and provides examples for these cases.

Blog Posts
----------

The motivation for this lib is further outlined in two of my blog posts:

- [Android Architecture: MV?](http://doridori.github.io/Android-Architecture-MV%3F/)
- [Android Architecture: Introducing Dynamo](http://doridori.github.io/) **UPDATE LINK**

Wiki
----

The Wiki for this repo contains documentation about how to use this library. [Check it out](https://github.com/doridori/Dynamo/wiki).

Download
--------

_jcenter coming soon_

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
