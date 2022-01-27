(ns example.immuconf-depsedn
  (:require [uap-clj.browser :as uab])
  (:gen-class))

(defn greet
  "Callable entry point to the application."
  [data]
  (println
   (uab/browser "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
