# Memento
A project for Mobile Device Software Development at the University of Central Florida.

## What is it?
Memento is a privacy-conscious, context-aware journaling and scrapbook app designed to help users notice and preserve experiences they might otherwise forget. The app uses contextual signals such as location, weather, activity, and elevation to suggest potential moments from their day. The user then decides which moments to keep private or to share, and what information each saved memory contains.

> **Your phone notices context; you decide what becomes a memory.**

## Features
- Account creation and email verification
- Opt-in permissions with clear explanations of how each type of data improves the experience
- A configurable Home Zone where familiar locations are not treated as new based on location alone
- Passive identification of Moment Candidates using enabled signals such as location, weather, activity, motion, and elevation
- Scheduled reflection notifications at user-selected days and times
- Daily review of suggested moments, with the option to save multiple moments, add one manually, or skip the day
- Memory creation with optional photos, reflections, mood, general location, weather, activity, and date/time
- A personal Scrapbook containing private Vignettes and friend-shared Postcards
- User-controlled sharing that determines which friends and contextual details can be included in a Postcard
- Friend-based social features without public followers, algorithmic discovery, or reposting
- Favorites for quickly revisiting meaningful memories
- Private or view-only shared albums for trips, events, and other periods of the user's life

## Moment Detection
Memento identifies Moment Candidates using only the data categories the user has enabled in their settings. Possible signals include visiting a new area, traveling outside the user's Home Zone, participating in an unusual activity, experiencing a significant elevation change, or encountering noteworthy weather. A rule-based Moment Score is used to determine whether a collection of signals is worth presenting during the user's daily reflection.

## Data and Privacy
Memento separates data collection, storage, saving, and sharing so the user remains in control at every stage:
- **Raw sensor data is temporary.** GPS samples, motion readings, and similar data are processed to detect activities and potential moments, then deleted when no longer needed.
- **Novelty history is minimal.** The app retains only summarized information required to recognize whether an experience is new, such as an approximate area, first and last visit dates, and visit count. It does not preserve a detailed trail of the user's movements.
- **Saved memory data is persistent.** Context such as location, weather, activity, time, mood, photos, and reflections is retained only when the user chooses to include it in a Vignette or Postcard.
- **Sharing is always deliberate.** Nothing detected by the app is automatically saved or shared. A user may keep the full journaling experience private and can hide selected details when sharing a Postcard.

## Project Specifications
- Mobile application supporting both portrait and landscape orientation
- Multiple screens for distinct tasks
- User input collection with persistent data storage
- Error handling for invalid input and failed operations
- System messages and notifications that provide feedback and reflection prompts
- Configurable access to supported phone sensors and contextual data
- Friend-based sharing with per-memory and per-album privacy controls

## Potential Stretch Goals
- More sophisticated novelty scoring, potentially using machine learning
- Advanced elevation and motion detection
- Smartwatch integration
- Heart-rate and workout context for memories
- Monthly and yearly Scrapbook retrospectives
- Additional album presentation and customization options
- Lightweight reactions and comments between friends

## Team
- Cameron Lowe
- Paul Bagaric
- Judhy Germain Bouchard
