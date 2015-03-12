(ns middlebrow.core)

(defprotocol IBrowser
  (container [self] "Gets the container type as a keyword. Such as `:fx`, `:swt`, `:thrust`, etc.")

  (show [self])
  (hide [self])
  (activate [self])
  (deactivate [self])
  (close [self])
  (visible? [self])

  (minimize [self])
  (maximize [self])
  (minimized? [self])
  (maximized? [self])

  (set-fullscreen [self fullscreen])
  (fullscreen? [self])

  (get-title [self])
  (set-title [self title])

  (get-x [self])
  (set-x [self x])
  (get-y [self])
  (set-y [self y])
  (get-position [self] "Gets the position of the window as a vector: [x y]")
  (set-position [self position] [self x y])

  (get-width [self])
  (set-width [self width])
  (get-height [self])
  (set-height [self height])
  (get-size [self] "Gets the size of the window as a vector: [width height]")
  (set-size [self size] [self width height])

  (get-url [self])
  (set-url [self url])

  ; Events
  (listen-closed [self handler])
  (listen-focus-gained [self handler])
  (listen-focus-lost [self handler]))

(defn container-of? [browser & container-ids]
  (let [c (container browser)]
    (boolean (some #(= c %) container-ids))))
