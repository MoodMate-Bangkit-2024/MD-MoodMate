# Mobile Development
The backend is built on Kotlin with several packages, including:

* **Kotlin:** The primary programming language for modern and robust Android app development.
* **Android Studio:** A comprehensive Integrated Development Environment (IDE) for Android app development.
* **Postman:** A tool for testing APIs, allowing developers to send HTTP requests and inspect their responses.
* **Figma:** A cloud-based design tool that enables collaboration on UI/UX design.
* **Dribbble:** A platform for finding design inspiration and showcasing design work.
* **JWT (JSON Web Token):** A standard for creating JSON-based tokens used for user authentication.

## System Architecture Design
### System Architecture
The diagram above illustrates the path that data follows, originating from the user, passing through the API endpoint, navigating through the backend architecture, and finally returning to the user.

When the user first submits their request, a POST request is triggered. This request is intercepted and processed by the Kotlin app using Retrofit. Retrofit then extracts the incoming data from the request body and passes it on for further processing.

After receiving the response from the server, the app stores it in a local Database using Room. Finally, the processed data is rendered and displayed to the user, completing the user interaction with the application.

## Mobile Development
### Table of Contents
Introduction
Datasets
Libraries
Models
Evaluation
Model Conversion
Introduction
This repository contains documentation for the Mobile Application Development component of the MoodMate Project. We are developing two main features for this project:

Mood Detection from Journaling: This feature classifies the user’s journal entries into one of four mood labels: angry, sad, fearful, and happy.

Chat Bot: This feature enables interaction with the user.
This document provides a comprehensive overview of the methodology, models, and development tools used in developing these features.

Datasets
Our project utilizes several datasets sourced from various repositories to support various aspects of our research and development. These datasets are integral to training and evaluating our development models and algorithms.

Libraries
In the MoodMate-Mobile project, various libraries and tools are used for data processing, model building, and evaluation. Here is a brief explanation of each:

Data Manipulation and Analysis

Retrofit: For handling and processing HTTP and API requests.
OkHttp: HTTP client for network interactions.
Data Processing and Storage

DataStore: For simple and efficient data storage.
Room: For more complex and relational local data storage.
Component Architecture

Lifecycle component: Manages the lifecycle of UI components.
ViewModel: For persistent data storage throughout the lifecycle of UI components.
Navigation component: For handling navigation between screens.
Image Processing

Glide: For image processing and loading.
Design and UI

Figma: For UI/UX design collaboration.
Dribbble: For design inspiration.
Models
The MoodMate-Mobile project includes two main features: Mood Detection from Journaling and Chatbot. Each feature uses a specific development model and architecture to achieve its functionality.

Mood Detection from Journaling
This feature classifies a user’s journal entries into one of four mood labels: angry, sad, scared, and happy. The model architecture for mood detection includes preprocessing steps to clean and tokenize text data, followed by a neural network to accurately classify moods.

Chat Bot
The Chat Bot feature allows interaction with the user, providing responses based on user input.

Evaluation
The MoodMate-Mobile project includes two main features: Mood Detection from Journaling and Chat Bot. The following is an evaluation of the Mood Detection from Journaling model based on its accuracy during training, validation, and testing. In addition, key performance metrics are provided, along with a confusion matrix for the test data.

Mood Detection from Journaling
This feature classifies the user’s journal entries into one of four mood labels: angry, sad, fearful, and happy.

Training and Validation Accuracy
The model was trained and validated using a prepared dataset. The following are the accuracy metrics for training and validation:

Training Accuracy: 90.4%
Validation Accuracy: 78.71%
Test Performance
The model was tested on a separate dataset to evaluate its performance. Here are the key performance metrics:

Accuracy: 92.60%
Precision (Weighted): 92.86%
Recall (Weighted): 92.60%
F1 Score (Weighted): 92.34%
Confusion Matrix
The confusion matrix below provides a detailed view of the model’s performance on the test data, showing actual versus predicted mood labels.
