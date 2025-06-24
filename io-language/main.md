## IO Language

### What is IO?
- Prototype-based, object-oriented scripting language.
- Dynamic typing, lightweight syntax, and homoiconic (code is data).
- Focus on simplicity, concurrency, and flexibility.

### Who created it, when, and why?
- Created by Steve Dekorte in 2002.
- Motivated to deeply understand interpreters and language design.
- Inspired by Smalltalk (objects), Self (prototypes), Lisp (homoiconicity), and Lua (embeddability).

### How to Set Up and Run Io?

**Official instructions (via [iolanguage.org](https://iolanguage.org/)):**

#### On macOS:

```bash
brew install io
```

#### On Linux:

```bash
sudo apt update
sudo apt install git cmake build-essential libssl-dev
git clone --recursive https://github.com/IoLanguage/io.git
cd io
cmake -DCMAKE_BUILD_TYPE=Release .
make
sudo make install
```

---

### 4. Basic Syntax and Language Concepts

#### Variables and Assignment

```io
x := 10     # define and assign
x = x + 1   # reassign
```

#### Methods

```python
sayHello := method("Hello" println)
sayHello
#Output: Hello
```

#### Loops

```python
for(i, 1, 5, i println)


i := 0
while(i < 3, i println; i = i + 1)
```

#### Conditionals

```python
x := 5
if(x > 3, "x is greater than 3" println)
```

#### Creating Objects

```python
Person := Object clone
Person greet := method("Hi, I’m a person." println)

bob := Person clone
bob greet
```

---

### Cool Features

#### Everything is an object, prototype-based inheritance

  ```python
  parent := Object clone
  child := parent clone
  ```

#### Message-oriented syntax

  ```python
  "Hello, world!" println
  ```

#### Homoiconicity

  ```io
    msg := Message clone setName("+")

    # Create message chain: 1 + 2
    msg setArguments(list(Message clone setName("2")))

    # Set the next message (like a method chain)
    plusMsg := Message clone setName("+")

    # We create a context to evaluate in
    context := Object clone
    context setSlot("x", 1)

    # This doesn't do 1 + 2 directly, but shows how to treat code as messages:
    context doMessage(msg)  # not meaningful alone, but shows the structure

  ```

#### Coroutines, Actors, Futures

  ```python
  odd := Object clone; odd print := method(1 println; yield; 3 println)
  even := Object clone; even print := method(0 println; yield; 2 println; yield)
  odd @print; even @print
  ```

#### Reflection & metaprogramming – Introspect and modify objects at runtime easily.

    Object slotNames println  # => list of all slot names in Object

#### Incremental GC – Memory management is handled automatically and incrementally to reduce pause times.
#### Embeddable VM – Io can be embedded in other programs to provide scripting capability.

---
### 6. Is Anyone Using It in the Real World?

- Io remains a **niche language**, mostly used in **educational**, **research**, and **language exploration** contexts.
- It has **no known large-scale enterprise adoption**, but inspired other languages (e.g. Ioke, Potion).
- It maintains an **open-source community**, with binaries and build support updated periodically.

---

### 7. Tooling & Development Experience

- **REPL:** Yes, run `io` in the terminal.
- **Syntax Highlighting:** Available for **VS Code**, **Vim**, **Emacs**, and others via community plugins.
- **Package Manager:** `Eerie`, though limited in scope. Example:
  ```bash
  io eerie install SomePackage
  ```

---

### 8. Embedding Io into C/C++ Projects

- Io’s VM is embeddable in C/C++ applications.

- Example of embedding:

  ```c
  IoObject *context = IoState_init();
  IoState_doCString_(context, "1 + 1 println");
  ```

- Useful for creating scripting interfaces inside a host application.

---

### 9. References

- [Io GitHub Repository](https://github.com/IoLanguage/io)
- [Io Documentation and Wiki](https://iolanguage.org/)
- [Installation Guide](https://github.com/IoLanguage/io#build-instructions)
- [Steve Dekorte Interview](https://www.artima.com/intv/visual.html)
- [Steve Dekorte’s Blog](https://stevedekorte.com)
- [Eerie Package Manager Guide](https://github.com/IoLanguage/eerie)
- [Seven Languages in Seven Weeks](https://codedocs.org/what-is/io-programming-language)
- [REPL](https://iolanguage.org/repl/index.html)

---