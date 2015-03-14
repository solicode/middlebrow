Middlebrow
==========

Middlebrow allows you to create standalone web apps that can be run on the desktop by hosting it inside of a browser/web-view. Middlebrow exposes a common interface, which currently has implementations for:

-	**[Thrust](https://github.com/breach/thrust)**: Chromium-based cross-platform application framework which is designed to be cross-language. Middlebrow uses the [clj-thrust](https://github.com/solicode/clj-thrust) bindings for its Clojure support.
-	**JavaFX**: Uses the built-in WebView component, which is based on WebKit.
-	**SWT**: Uses the built-in Browser widget, which has support for WebKit, Firefox, and IE.

Overview
--------

Middlebrow allows you to write desktop GUI applications as if you were writing a web app ([Atom](https://github.com/atom/atom) and [Light Table](https://github.com/LightTable/LightTable) are 2 popular applications that follow a similar approach).

By doing so, it becomes possible to target the desktop and web simultaneously with the same codebase without requiring too much extra effort (of course, this also depends on how you design your project and what kind of dependencies you have).

Also, since Middlebrow is written in Clojure, it's possible to use it to write applications where both the server and client code are written entirely in Clojure and ClojureScript. And you can do all this with a REPL and live code reloading.

Since Middlebrow has a common interface for its multiple web-view containers/hosts, you can switch between them with minimal code changes.

Getting Started
---------------

### Installation

Add the following dependency to your `project.clj` file:

```clojure
[net.solicode/middlebrow "0.1.0-SNAPSHOT"]
```

Additionally add one of the following dependencies, depending on which container you'd like to use:

```clojure
[net.solicode/middlebrow-fx "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-thrust "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-swt.win32.win32.x86 "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-swt.win32.win32.x86_64 "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-swt.gtk.linux.x86 "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-swt.gtk.linux.x86_64 "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-swt.cocoa.macosx "0.1.0-SNAPSHOT"]
[net.solicode/middlebrow-swt.cocoa.macosx.x86_64 "0.1.0-SNAPSHOT"]
```

### Examples

The simplest example illustrating how to use Middlebrow is the following:

```clojure
(ns my-app.core
  (:require [middlebrow.core :as mb]
            [middlebrow-fx.core :as fx]))

(let [window (fx/create-window
			 :url "http://localhost:8000" ; URL to your web app
			 :width 800
			 :height 600
			 :title "My App")]
   (mb/show window)
   (mb/activate window))
```

The above example uses JavaFX, but you can similarly use SWT or Thrust as well.

### Sample Project

For a more complete example which includes both server and client code, you can take a look at [this sample project](https://github.com/solicode/middlebrow-example-notepad). It's a very basic Notepad-like application which shows how one might structure an app that uses Middlebrow.

FAQ
---

### Which container should I use?

There's no simple answer to this, as it depends on what your app does, how large it is, and who your users are. But if you'd like to know how I'd summarize these options, here you go:

**Thrust**: This should be the most performant option (though I say this without any real numbers to back it up, so do take what I'm saying with a grain of salt). So while it's bleeding-edge, distributing your app with it takes a bit of extra work (the same is true for Atom Shell). The Thrust runtime is quite large, and this could be problematic for you depending on your requirements. If the user already has the Thrust runtime, you could possibly make use of that instead of packaging Thrust in each of your applications.

**JavaFX**: The JavaFX WebView is WebKit-based, and seems to perform well enough for the apps that I've tested it with. I've noticed some quirks here and there on my Windows machine, like redraw issues when resizing, but I don't know if that's just me or not.

The JavaFX option is quite easy to get up and running though. Packaging it might also be the simplest out of these 3 options.

**SWT**: Its browser engine can be WebKit, Firefox, or IE depending on the OS its running on and what is installed on the user's machine. This might make it harder to test for. But since the windowing framework uses native components, I've found that it "feels" good when it comes to the subtle things: scrollbars, IME support (for example, when I'm typing Japanese), and so on.

Developing with SWT is definitely the most painful out of these options, however. Especially on OSX since the SWT display must be created on the main thread. Because of this, it doesn't seem to play nice with REPL development.

### What about Atom Shell or NW.js?

While it might possible to support these in the future, it's going to require a lot more work. I haven't given it much thought yet. I would just like to avoid any JVM dependencies if possible when going this route. Requiring both the JVM and Node seems like a bit much to me. I'm not yet convinced making either of these compatible with Middlebrow makes much sense.

You can already use ClojureScript if you want to use Atom Shell directly. Going all-in with it doesn't seem like a bad idea given that it's backed by GitHub and is used in a large project like Atom.

Future Work
-----------

-	Deploy SWT versions for each OS/architecture combination.
-	Abstract out other useful functionality with a common interface for all of the containers
	-	Open/Save dialogs
	-	Menus
	-	Tray icon

License
-------

Copyright © 2015 Solicode

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
