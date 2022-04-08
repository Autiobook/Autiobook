# Autiobook

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Turn any big block of text into an audiobook you can listen to on the go.

### App Evaluation
- **Category:** Utility
- **Mobile:** Portable. Could be anything that has a frontend allowing for a file upload.
- **Story:** Allows users to listen to any text media easily, not just books. 
- **Market:** Audiobooks are a massive market for people who wish to listen to a book while doing mundane tasks such as driving or cleaning. This would open that market towards more non traditional types of text media such as fanfiction or webnovels.
- **Habit:** People already listen to audiobooks a lot shown by the popularity of apps such as Audible, which are very specifically geared towards a selection of books.
- **Scope:** Create audio from text media in general. If the API is sold per call, it could be useful to many businesses looking to create training materials, etc.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* API:
    * Deployed on cloud (preferrably serverless computing to save on costs).
    * Convert a text input (.txt only) into an audio output (mp3).
        * Speech synthesis model, must be computationally inexpensive.
    * Authenticated by an API key.
        * Different types of keys: Rate limited and unlimited.


* Mobile Frontend:
    * Sign up system (auto generate rate limited API key?).
    * Login system.
    * Ability to upload a file to generate and download audiobooks.
    * Feed to scroll through generated audiobooks.
    * Playback audiobooks on the feed.
    * User should not be able to log out.
        * Reason: Make it harder to repeatedly generate new API keys by registering many accounts over again.
    * Switch between the feed and upload screens.
    * No generated audiobooks should be stored on any server. Everything should be stored in a local directory. (**IMPORTANT**)

**Optional Nice-to-have Stories**

<!-- * [fill in your required user stories here] -->
* Locally convert several types of text formats into txt.
    * PDF, RTF, JSON, EPUB
* Allow camera to scan text.
* Supporting multiple text and image formats.
* Good styling.
* Dark mode, light mode.
* Bookmarks to pick up where you left off.
* OAuth to replace login and register screens. (Would make avoiding rate limits MUCH more difficult.)

### 2. Screen Archetypes

* [Register/Login]
   * User can register, login and maybe logout 
* [Upload]
    * User can upload files on this page
* [Feed]
   * Scroll through feed of audio books
* [Detail]
   * User can listen to the specific book

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* [Login/Register]
    * User can login or register
* [Feed/Upload]
    * User can click on these icon which lead you to that page (bottom navigation)
* [Detail]
    * Click on audio book from feed to navigate to detail

**Flow Navigation** (Screen to Screen)

* Register -> Feed
* Login -> Feed
* Feed -> Upload
* Feed -> Detail

## Wireframes
![](https://i.imgur.com/WPSxZgY.jpg)

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
User
| Property      | Type          | Description  |
| ------------- |---------------|--------------|
| objectId      | String        | unique id for the user post (default field)    |
| username      | String        | unique username for authentication             |
| password      | String        | password for authentication                    |
| createdAt     | DateTime      | date when post is created (default field)      |
| createdWith   | Object        | Information about how this session was created |
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
##### Google Text to Speech API
Base URL - [https://texttospeech.googleapis.com](https://texttospeech.googleapis.com)

HTTP Verb | Endpoint | Description
----------|----------|------------
`POST`    | /v1beta1/text:synthesize | Synthesizes speech synchronously: receive results after all text input has been processed.


