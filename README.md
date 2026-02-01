# Sobo App TPL (sobo_app_tpl_kt)
- a library of useful utils and Application Template for Kotlin projects

Author:     Krzysztof Sobolewski <krzysobo_dev@o2.pl>
Website:    https://www.krzysztofsobolewski.info/
GitHub:     https://github.com/krzysobo/sobo_app_tpl_kt
            https://github.com/krzysobo/
LinkedIn:   https://www.linkedin.com/in/krzysztof-sobolewski-39694a103/

License:    Apache 2.0
version:    1.0.1

for now, it can only be implemented by implementation(project(":soboAppTpl")
!!!

CHANGES in version 1.0.1 (2026-02-01)
- AnyImage class for type-blind image container
- Fixes in Router - Backstack handling: a call to the no longer existing "removeLast" function in backStack has been replaced with backStack.removeAt(backStack.lastIndex)
- more workers for Settings, including one for Rushwolf's library
- tidying-up the code :)
