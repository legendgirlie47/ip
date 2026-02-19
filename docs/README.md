# 🍅 Ketchup User Guide

## 📖 Introduction

**Ketchup** is a lightweight task management chatbot that helps you organise and manage your tasks using simple text commands.

With Ketchup, you can:

- Add To-Dos
- Add Deadlines
- Add Events
- Mark and unmark tasks
- Delete tasks
- View all tasks

All interactions are done through text commands in the chatbot interface.

---
## User Interface

![Ketchup UI](images/ui.png)
---

## 🚀 Quick Start

### Requirements

- Java 17 installed

### Running the Application

Open a terminal in the folder containing `ketchup.jar` and run:

```bash
java -jar ketchup.jar
```

The chatbot window will launch.

---

## 📋 Command Summary

| Command | Description |
|----------|-------------|
| `todo` | Add a to-do task |
| `deadline` | Add a task with a deadline |
| `event` | Add an event with start and end time |
| `list` | View all tasks |
| `mark` | Mark a task as done |
| `unmark` | Mark a task as not done |
| `delete` | Delete a task |
| `help` | Show full command guide |
| `bye` | Exit the application |

---

# 🧩 Features

## ➕ Adding a To-Do

Adds a simple task without any date or time.

### Format

```
todo <task description>
```

### Example

```
todo read book
```

---

## ⏰ Adding a Deadline

Adds a task that must be completed by a specific date and time.

### Format

```
deadline <task description> /by yyyy-MM-dd HHmm
```

### Example

```
deadline submit report /by 2026-02-20 1800
```

### Date Format

```
yyyy-MM-dd HHmm
```

Example:

```
2026-02-20 1800
```

---

## 📅 Adding an Event

Adds an event with a start and end date-time.

### Format

```
event <event description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm
```

### Example

```
event team meeting /from 2026-02-20 1400 /to 2026-02-20 1600
```

---

## 📋 Viewing All Tasks

Displays all tasks in a numbered list.

```
list
```

---

## ✅ Marking a Task as Done

Marks a task as completed.

### Format
```
mark <task number>
```

### Example:

```
mark 2
```

---

## ↩ Unmarking a Task

Marks a task as not completed.

### Format
```
unmark <task number>
```
### Example:

```
unmark 2
```

---

## 🗑 Deleting a Task

Deletes a task permanently.

### Format
```
delete <task number>
```
### Example:

```
delete 2
```
---

## 📘 Help Command

Displays the full command guide.

```
help
```

---

## 👋 Exiting the Application

Closes Ketchup.

```
bye
```

---

# 💾 Task Storage

- Tasks are automatically saved after every modification.
- When you reopen Ketchup, your tasks will still be available.

---

# ⚠️ Error Handling

If an invalid command is entered, Ketchup will respond with an error message.

Common errors include:

- Missing date format
- Missing task number
- Invalid task index
- Incorrect command format

Ensure that commands follow the specified format exactly.

---

# 📝 Command Cheat Sheet

```
todo read book
deadline homework /by 2026-02-20 1800
event meeting /from 2026-02-20 1400 /to 2026-02-20 1600
list
mark 1
unmark 1
delete 2
find report
help
bye
```
