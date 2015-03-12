(ns middlebrow.util
  (:import [java.io InputStream PrintWriter StringWriter]))

(defmacro first-non-nil
  "Returns the first argument which is not nil."
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [x# ~x]
      (if (nil? x#) (first-non-nil ~@next) x#))))

(defmacro set-fields!
  "Repeatedly calls set! for all field/value pairs passed in"
  [object & fields-and-values]
  (let [object-sym (gensym)]
    `(let [~object-sym ~object]
       ~@(for [[field value] (partition 2 fields-and-values)]
           `(set! (. ~object-sym ~field) ~value)))))

(defn throwable->string
  "Converts a throwable to a string of the full stack trace."
  [t]
  (let [sw (StringWriter.)]
    (.printStackTrace ^Throwable t (PrintWriter. sw))
    (.toString sw)))

(defn ^InputStream resource-as-stream
  ([n] (resource-as-stream n (.getContextClassLoader (Thread/currentThread))))
  ([n ^ClassLoader loader] (.getResourceAsStream loader n)))