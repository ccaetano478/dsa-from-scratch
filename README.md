# dsa-from-scratch

A personal study project where I implement classic data structures from the ground up in Java — no shortcuts, no built-in collections, just the raw logic.

The goal isn't to replace Java's standard library. It's to understand *why* things work the way they do by building them myself.

Each structure lives in its own directory with:
- A `HOW-TO.md` explaining the reasoning behind the implementation
- One or more `.java` implementations
- Unit tests so anyone can verify the behavior

---

## Structures

| Structure | Directory | Status |
|---|---|---|
| Array | `arrays/` | ✅ Done |

---

## How to run the tests

> Requirements: Java 25, JUnit 5

```bash
# from the project root
./gradlew test
```

---

## Philosophy

This is a learning exercise, not a production library. The implementations are intentionally simple and sometimes limited — the point is understanding the concept, not optimizing it.

Where Java gets in the way (like memory allocation), I document *why* and what would be different in a lower-level language like C or Rust.