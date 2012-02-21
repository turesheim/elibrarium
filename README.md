#Elibrārium
The name of this project is "elibrārium" which is a word constructed from the English adjective "electronic" and the Latin noun "librārium", meaning bookcase or library. Putting those two toghether should be sufficient to describe the purpose of the project – which is essentially to produce an electronic library and book reader. More specifically an [EPUB](http://en.wikipedia.org/wiki/EPUB) library manager and book reader built on top of the [Eclipse Platform](http://www.eclipse.org/platform/).

> Please note that this project is in it's early stages. So while there is a working EPUB reader – it has not yet reached release quality.

### Supported platforms

The reader is relying on the WebKit HTML rendering engine so this must be present on the host system. On OS X and i.e. Ubuntu 10.04 this is already the case. On Windows Safari must be installed. Also note that Eclipse version 3.7 or newer is required.

<table>
<tr><th>Platform</th><th>Requirements</th></tr>
<tr><td>Mac OS X 10.7</td><td>None in particular</td></tr>
<tr><td>Windows 7</td><td>32-bit SWT, Safari must be installed</td></tr>
<tr><td>Linux</td><td>WebKitGTK 1.2.0 or newer</td></tr>
</table>

##Features
The [planned set of features](elibrarium/wiki/Plan) are basically those you expect to find in any modern reading system, such as book-like navigation, table of contents, search, bookmarks along with support for adding notes and marking text.

##License
This software is released under the [Eclipse Public License 1.0](http://www.eclipse.org/legal/epl-v10.html).

##Copyright
Copyright (c) 2011,2012 Torkild Ulvøy Resheim, All Rights Reserved.