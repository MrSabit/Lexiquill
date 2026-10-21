# Lexiquill - Project Notes & Architecture

## What the App Is
**Lexiquill** is an offline-first, ambient vocabulary and micro-learning Android application designed to help users build and retain vocabulary effortlessly. Instead of forcing long, dedicated study sessions, Lexiquill integrates learning into daily life through subtle notifications and intermittent spaced repetition reminders.

---

## Database Tables & Fields

Lexiquill uses Room Database locally with three primary tables:

### 1. `Word` Table
Stores vocabulary words and phrases added by the user.
- `id` (`INTEGER`, Primary Key, Auto-generate)
- `text` (`TEXT`, Unique index): The word or phrase itself (stored lowercased and trimmed, max 60 chars).
- `meaning` (`TEXT`, Nullable): Optional definition or meaning of the word.
- `added_at` (`INTEGER` / timestamp): Epoch milliseconds when the word was added.
- `box` (`INTEGER`, default `1`): Spaced repetition Leitner box level.
- `last_reviewed_at` (`INTEGER` / timestamp, Nullable): Epoch milliseconds of the last review.

### 2. `Review` Table
Records historical spaced repetition review events and outcomes.
- `id` (`INTEGER`, Primary Key, Auto-generate)
- `word_id` (`INTEGER`, Foreign Key referencing `words(id)` with CASCADE delete)
- `reviewed_at` (`INTEGER` / timestamp): Epoch milliseconds when the review occurred.
- `correct` (`INTEGER` / boolean): Whether the user answered correctly.

### 3. `Interruption` Table
Tracks scheduled and delivered ambient micro-learning reminders/interruptions.
- `id` (`INTEGER`, Primary Key, Auto-generate)
- `scheduled_at` (`INTEGER` / timestamp): When the reminder was scheduled.
- `fired_at` (`INTEGER` / timestamp, Nullable): When the notification actually fired.
- `responded_at` (`INTEGER` / timestamp, Nullable): When the user interacted with the notification.
- `outcome` (`TEXT`, default `"PENDING"`): Status (`PENDING`, `ANSWERED`, `DECLINED`, or `IGNORED`).
- `mode` (`TEXT`, default `"RANDOM"`): Scheduling mode (`RANDOM` or `FIXED`).

---

## Screens & UI Components (So Far)

1. **Manual Word Management & List Screen (`MainActivity`)**
   - **Top Section**: Input fields for word/phrase (with validation for emptiness and max length <= 60 chars) and optional meaning, plus an "Add" button and status/error feedback ("Already saved", success messages).
   - **Word List Section**: Scrollable `LazyColumn` displaying all saved words ordered by `added_at DESC` (newest first). Each item shows the word, meaning (if present), added timestamp, Leitner box, and a delete button with a confirmation dialog.
   - **Empty State**: Clear placeholder message when no words have been added yet.

---

## Architecture & Design Decisions

- **Native Android Only**: Built entirely in Kotlin and Jetpack Compose without reliance on cross-platform frameworks.
- **Local Data Only**: All vocabulary and study states are stored locally on-device using Room Database. No cloud account or external server required.
- **Inexact Scheduling Only**: Uses Android AlarmManager / WorkManager with inexact intervals only (`setInexactRepeating` / WorkManager flexible intervals). **No `SCHEDULE_EXACT_ALARM` permission is required or requested**, ensuring respectful battery usage and zero system permission friction.
- **Notification-First Reminders**: Reminders are delivered directly via interactive notifications (`NotificationCompat`), allowing users to engage with micro-learning without needing to open the app UI.
- **Pure Compose & Material 3**: Adheres strictly to Material 3 design guidelines with edge-to-edge support, adaptive layouts, and clean reactive state flows.
