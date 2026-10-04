# ⚡ Grid-Weaver

> Real-Time Smart Grid Monitoring Dashboard powered by Spring Boot, React, WebSocket and live NPP / MERIT India grid data.

Grid-Weaver is a real-time smart grid monitoring system that collects and displays India's national power-grid data through a Spring Boot backend and a React-based dashboard.

The dashboard provides live visibility into electricity demand, generation, grid import/export and generation sources such as thermal, hydro, wind, gas, nuclear and solar.

---

## 🚀 Live Demo

### Dashboard

https://grid-weaver-frontend.onrender.com

The application is deployed using Render.

---

## 📌 Project Overview

Grid-Weaver connects a Spring Boot backend with a React frontend to provide a real-time view of the Indian power grid.

The backend retrieves grid information from the NPP / MERIT India data source and exposes the information through REST APIs.

Real-time updates are delivered to the frontend using WebSocket communication.

### Main flow

```text
NPP / MERIT India
       ↓
Spring Boot Backend
       ↓
REST APIs + WebSocket
       ↓
React Dashboard
       ↓
Real-Time Grid Monitoring