This project module is built into the main platform 
executable. It is a relatively compact core. The rest 
of the browser/platform is made of plugins. The
module Primary_Extension contains all the essential
browser functionality.

This module is released under the GPL license.

These are the *VM* arguments currently (5/20/2007) needed
to run Lobo from source (in Eclipse at least):

-Dext.dirs=/tmp/XAMJ_Project/XAMJ_Build/ext -Dext.files=/tmp/XAMJ_Project/Primary_Extension,/tmp/XAMJ_Project/Common,/tmp/XAMJ_Project/HTML_Renderer,/tmp/XAMJ_Project/cssparser/bin,/tmp/XAMJ_Project/JWebContentExtension

The following are recommended *program* arguments to run
from source:

-debug

The entry point class is org.lobobrowser.main.EntryPoint.

----------------------------------------------------------------
NOTE: All Lobo windows should be closed before launching the
browser, particularly after you've made code changes. EntryPoint 
will try to reuse any running instances.
----------------------------------------------------------------
