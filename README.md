# immuconf-depsedn

Example repo to reproduce bug from running `clojure -M:uberjar` with [uap-clj 1.3.6](https://github.com/ua-parser/uap-clj) in the deps.edn

The `uberjar` command is supplied by the [uberdeps library](https://github.com/tonsky/uberdeps)

```bash
> clojure -M:uberjar

+ uap-clj/uap-clj #:mvn{:version "1.3.7"}
.   clj-commons/clj-yaml #:mvn{:version "0.7.0"}
.     org.flatland/ordered #:mvn{:version "1.5.7"}
.       org.flatland/useful #:mvn{:version "0.11.6"}
.         org.clojure/tools.macro #:mvn{:version "0.1.1"}
.     org.yaml/snakeyaml #:mvn{:version "1.24"}
.   russellwhitaker/immuconf #:mvn{:version "0.3.1"}
Execution error at uberdeps.api/fn (api.clj:108).
No dispatch macro for: ?
```

After some digging into uberdeps /src/, see -- https://github.com/tonsky/uberdeps/blob/master/src/uberdeps/api.clj#L115

```clj
(def default-mergers
  {"META-INF/plexus/components.xml" components-merger
   #"META-INF/services/.*"          services-merger
   #"data_readers.clj[cs]?"         clojure-maps-merger})
```

I believe the data_readers file in Immuconf is causing this issue. https://github.com/russellwhitaker/immuconf/blob/master/src/data_readers.cljc

``` clj
#?(:cljs (ns immuconf.data-readers
           (:require [immuconf.config]
                     [cljs.reader :as reader])))
#?(:clj {immuconf/override immuconf.config/->Override
         immuconf/default immuconf.config/->Default})
```

Not sure of exact cause. There's some magic going on in `immuconf.config` with an `ns-unmap`, but I'm submitting this repo to help investigation.

## License

Copyright (c) 2015-2020 Quest Yarbrough

Distributed under the Eclipse Public License version 1.0.
