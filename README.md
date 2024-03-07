# LogOnEx

The application is built following the Clean Architecture principles, which promotes a separation of concerns and modular design. It consists of three main layers:

1) Presentation Layer (UI): Contains the View components responsible for displaying data and interacting with users. In this application, the UI components follow the MVVM architecture pattern, ensuring a clear separation between the UI logic and business logic.
2) Domain Layer: Contains the business logic and use cases of the application. It defines the operations that can be performed on the data and the business rules that govern those operations.
3) Data Layer: Responsible for data handling, including fetching data from external API, parsing it into a format usable by the application and caching in local Database

Jetpack Compose was used for constructing the application's user interface (UI).

Products caching policy:
The product data is refreshed each time the application starts and by tapping the 'Refresh' button on the Categories screen.