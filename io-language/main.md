## io Language

### What is io?
- Prototype-based, object-oriented scripting language.
- Dynamic typing, lightweight syntax, and homoiconic (code is data).
- Focus on simplicity, concurrency, and flexibility.

### Who created it, when, and why?
- Created by Steve Dekorte in 2002.
- Motivated to deeply understand interpreters and language design.
- Inspired by Smalltalk (objects), Self (prototypes), Lisp (homoiconicity), and Lua (embeddability).

### How to Set Up and Run io?

**Official instructions (via [iolanguage.org](https://iolanguage.org/)):**
#### Download binary files https://iolanguage.org/binaries.html

#### Build it locally

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

### Basic Syntax and Language Concepts

#### Variables and Assignment

```io
x := 10     // define and assign
x = x + 1   # reassign
```

#### Methods

```io
sayHello := method("Hello" println)
sayHello # Output: Hello
```

#### Loops

```io
# for(variable, start, end, [step], body)
for(i, 1, 5, i println)


i := 0
# while(condition, body)
while(i < 3, i println; i = i + 1)
```

#### Conditionals

```io
x := 5
if(x > 3, "x is greater than 3" println)
```

#### Creating Objects

```io
Person := Object clone
Person name := nil
Person setName := method(name, self name = name)
Person greet := method("Hi, I'm " .. self name println)

bob := Person clone
bob setName("Bob")
bob greet  # Output: Hi, I'm Bob
```
---

#### Coroutines

```io

counter := Object clone
counter count := method(
  for(i, 1, 3,
    "Counter: " .. i println
    yield  # Pause here and let others run
  )
)

printer := Object clone
printer printLetters := method(
  list("A", "B", "C") foreach(letter,
    "Printer: " .. letter println
    yield  # Pause here and let others run
  )
)

counter @count
printer @printLetters

# Output: 
# A
# 1
# B
# 2
# C
# 3
```
- `yield` pauses the current coroutine, allowing others to run.
- `@` starts a method as a coroutine (actor).
- `resume` continues a paused coroutine.
- The output will alternate between numbers and letters, showing cooperative multitasking.

#### Message-oriented syntax

  ```io
  "Hello, world!" println
  ```

#### Introspect and modify objects at runtime

```
Person slotNames println
# Output: list(greet, type)

# Add a new slot (property) at runtime
Person age := 42
Person slotNames println  # Now includes "age"
Person age println        # Output: 42
# Output : list(greet, type, age)

# remove a slot at runtime
Person removeSlot("age")
Person slotNames println
# Output : list(greet, type)

# define a method
Person sayAge := method("My age is " .. self age println)
Person age := 30
Person sayAge             # Output: My age is 30

# source code of a method
Person greet code println
# Output: method("Hi, I'm a person." println)

```

---
### Who is using it?

- Io is mostly used in educational, research, and language exploration contexts.
- It has no known large-scale enterprise adoption, but inspired other languages (e.g. Ioke, Potion).
- It maintains an **open-source community**, with binaries and build support updated periodically.

---

### Tooling & Development Experience

- [REPL for studying](https://iolanguage.org/repl/index.html)

- **REPL:** Yes, run `io` in the terminal.
- **Package Manager:** `Eerie`, though limited in scope. Example:
  ```bash
  io eerie install SomePackage
  ```

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



## Extra

#### Coroutines and Futures Example

```io
# Define a slow operation
slowAdder := Object clone
slowAdder add := method(a, b,
  wait(2)  # Simulate a delay (wait 2 seconds)
  a + b
)

# Start the operation asynchronously using futureSend
futureResult := slowAdder futureSend(add(10, 20))

# Do something else while waiting
"Calling another service..." println

# Io will wait until it is ready
"Result: " .. futureResult println

# Output:
# Doing other work...
# (2 second pause)
# Result: 30
```

#### Homoiconicity

```io
# Build the code as data: 1 + 2
msg := Message clone setName("+")
msg setArguments(list(Message clone setName("2")))

root := Message clone setName("1")
root setNext(msg)

# Evaluate the message chain in the current context
result := self doMessage(root)
result println  # Output: 3
```
