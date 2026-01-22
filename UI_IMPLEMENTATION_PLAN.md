# Medicine API - UI Implementation Plan

## Overview
Modern, responsive UI for Medicine API using **Thymeleaf + Tailwind CSS**

---

## Phase 1: Project Setup & Foundation

### Step 1.1: Add Dependencies
- [ ] Add Thymeleaf dependency to `pom.xml`
- [ ] Add Thymeleaf Layout Dialect dependency

### Step 1.2: Create Folder Structure
```
src/main/resources/
├── templates/
│   ├── layout/
│   │   └── main.html
│   ├── fragments/
│   │   ├── header.html
│   │   ├── footer.html
│   │   ├── alerts.html
│   │   └── pagination.html
│   ├── dashboard/
│   │   └── index.html
│   ├── medicine/
│   │   ├── list.html
│   │   ├── detail.html
│   │   ├── form.html
│   │   ├── expired.html
│   │   └── low-stock.html
│   └── error/
│       ├── 404.html
│       └── error.html
└── static/
    └── js/
        └── app.js
```

### Step 1.3: Create Base Layout
- [ ] Create `templates/layout/main.html`
  - HTML5 doctype
  - Tailwind CSS CDN link
  - Common head section (meta tags, title placeholder)
  - Body structure with content placeholder
  - Footer section

---

## Phase 2: Reusable Fragments

### Step 2.1: Navigation Fragment
- [ ] Create `templates/fragments/header.html`
  - Logo/brand name
  - Navigation links:
    - Dashboard
    - Medicines
    - Expired
    - Low Stock
  - Responsive mobile menu (hamburger)

### Step 2.2: Footer Fragment
- [ ] Create `templates/fragments/footer.html`
  - Copyright info
  - Version/year

### Step 2.3: Alert Fragment
- [ ] Create `templates/fragments/alerts.html`
  - Success messages (green styling)
  - Error messages (red styling)
  - Warning messages (yellow styling)
  - Auto-dismiss functionality

### Step 2.4: Pagination Fragment
- [ ] Create `templates/fragments/pagination.html`
  - Previous/Next buttons
  - Page numbers with active state
  - "Showing X of Y" info
  - Items per page selector (optional)

---

## Phase 3: Web Controllers

### Step 3.1: DashboardController
- [ ] Create `controller/web/DashboardController.java`
  - `GET /` → Redirect to dashboard
  - `GET /dashboard` → Dashboard page
  - Inject: MedicineService
  - Model attributes: stats, recentExpired, recentLowStock

### Step 3.2: MedicineWebController
- [ ] Create `controller/web/MedicineWebController.java`
  - `GET /medicines` → Paginated list page
  - `GET /medicines/{id}` → Detail page
  - `GET /medicines/new` → Empty create form
  - `POST /medicines` → Handle create submission
  - `GET /medicines/{id}/edit` → Pre-filled edit form
  - `POST /medicines/{id}` → Handle update submission
  - `POST /medicines/{id}/delete` → Handle delete
  - `GET /medicines/search` → Search results page
  - `GET /medicines/expired` → Expired medicines page
  - `GET /medicines/low-stock` → Low stock page

### Step 3.3: StockWebController
- [ ] Create `controller/web/StockWebController.java`
  - `POST /medicines/{id}/stock/increase` → Increase stock
  - `POST /medicines/{id}/stock/decrease` → Decrease stock
  - Redirect back to detail page with flash message

---

## Phase 4: Dashboard Page

### Step 4.1: Create Dashboard View
- [ ] Create `templates/dashboard/index.html`
  - **Stats Cards Row:**
    - Total Medicines (with icon)
    - Total Stock Units
    - Expired Count (red highlight if > 0)
    - Low Stock Count (yellow highlight if > 0)
  - **Quick Actions Section:**
    - "Add New Medicine" button
    - "View All Medicines" link
  - **Alerts Section:**
    - List of expired medicines (max 5)
    - List of low stock medicines (max 5)
    - "View All" links

---

## Phase 5: Medicine List & Search

### Step 5.1: Medicine List Page
- [ ] Create `templates/medicine/list.html`
  - **Search/Filter Bar:**
    - Name search input (min 3 chars)
    - Manufacturer dropdown filter
    - Search button
    - Clear filters link
  - **Data Table:**
    - Columns: ID, Name, Manufacturer, Expiry Date, Stock, Status, Actions
    - Status badges:
      - 🔴 Expired (red)
      - 🟡 Low Stock (yellow)
      - 🟢 OK (green)
    - Action buttons: View, Edit, Delete
  - **Pagination Component**
  - **"Add New" Button**

### Step 5.2: Search Results Display
- [ ] Reuse list.html template
  - Show "Search results for: X" header
  - Display matching medicines
  - "Clear search" button

---

## Phase 6: Medicine CRUD Forms

### Step 6.1: Create/Edit Form
- [ ] Create `templates/medicine/form.html` (shared template)
  - **Form Fields:**
    - Name (text input, required)
    - Manufacturer (dropdown with enum values, required)
    - Expiry Date (date picker, required, min=today for new)
    - Stock (number input, required, min=0)
  - **Validation:**
    - Client-side HTML5 validation
    - Server-side error display (field-level)
  - **Buttons:**
    - Submit (Create/Update based on mode)
    - Cancel (back to list)
  - **Conditional Logic:**
    - Hide manufacturer field on edit (not editable per API)
    - Different titles for create vs edit

### Step 6.2: Detail Page
- [ ] Create `templates/medicine/detail.html`
  - **Medicine Info Card:**
    - Name (large heading)
    - Manufacturer badge
    - Expiry date with status indicator
    - Current stock with visual bar
  - **Stock Adjustment Section:**
    - Quantity input field
    - "+" Increase button
    - "-" Decrease button
    - Current stock display updates
  - **Action Buttons:**
    - Edit button
    - Delete button (with confirmation)
  - **Navigation:**
    - Back to list link
    - Previous/Next medicine (optional)

---

## Phase 7: Filtered Views

### Step 7.1: Expired Medicines Page
- [ ] Create `templates/medicine/expired.html`
  - **Warning Banner:**
    - Red/orange alert styling
    - "X medicines have expired" message
  - **Table:**
    - Same structure as list
    - Additional column: "Days Overdue"
    - Highlight rows in red
  - **Empty State:**
    - "No expired medicines" success message

### Step 7.2: Low Stock Page
- [ ] Create `templates/medicine/low-stock.html`
  - **Alert Banner:**
    - Yellow warning styling
    - "X medicines are running low" message
  - **Stock Threshold Input:**
    - Default: 10
    - "Update" button to refresh
  - **Table:**
    - Same structure as list
    - Quick "Restock" button per row
    - Stock level progress bar
  - **Empty State:**
    - "All medicines adequately stocked" message

---

## Phase 8: JavaScript & Interactions

### Step 8.1: Create Main JS File
- [ ] Create `static/js/app.js`

### Step 8.2: Stock Adjustment Functionality
- [ ] Implement in `app.js`:
  - Quantity input validation (positive numbers only)
  - Form submission handling
  - Loading state on buttons
  - Success/error feedback

### Step 8.3: Delete Confirmation
- [ ] Implement in `app.js`:
  - Confirmation modal/dialog
  - "Are you sure?" message
  - Show warning if stock > 0
  - Cancel/Confirm buttons

### Step 8.4: Auto-dismiss Alerts
- [ ] Implement in `app.js`:
  - Auto-hide success messages after 5 seconds
  - Manual dismiss button

---

## Phase 9: Error Handling & Polish

### Step 9.1: Error Pages
- [ ] Create `templates/error/404.html`
  - "Page Not Found" message
  - Back to home link
  - Friendly illustration (optional)

- [ ] Create `templates/error/error.html`
  - Generic error message
  - Error details (in dev mode)
  - Back to home link

### Step 9.2: Form Validation Feedback
- [ ] Style validation errors
  - Red border on invalid fields
  - Error message below field
  - Highlight on submit

### Step 9.3: Responsive Design
- [ ] Test and adjust:
  - Mobile navigation (hamburger menu)
  - Table horizontal scroll on mobile
  - Card layouts on tablet
  - Touch-friendly buttons

---

## Files Summary

| # | File Path | Description |
|---|-----------|-------------|
| 1 | `pom.xml` | Add Thymeleaf dependencies |
| 2 | `templates/layout/main.html` | Base layout template |
| 3 | `templates/fragments/header.html` | Navigation component |
| 4 | `templates/fragments/footer.html` | Footer component |
| 5 | `templates/fragments/alerts.html` | Flash messages |
| 6 | `templates/fragments/pagination.html` | Pagination component |
| 7 | `controller/web/DashboardController.java` | Dashboard routes |
| 8 | `controller/web/MedicineWebController.java` | Medicine UI routes |
| 9 | `controller/web/StockWebController.java` | Stock adjustment routes |
| 10 | `templates/dashboard/index.html` | Dashboard view |
| 11 | `templates/medicine/list.html` | Medicine table view |
| 12 | `templates/medicine/form.html` | Create/Edit form |
| 13 | `templates/medicine/detail.html` | Single medicine view |
| 14 | `templates/medicine/expired.html` | Expired list view |
| 15 | `templates/medicine/low-stock.html` | Low stock view |
| 16 | `static/js/app.js` | JavaScript interactions |
| 17 | `templates/error/404.html` | Not found page |
| 18 | `templates/error/error.html` | General error page |

---

## API Endpoints → UI Mapping

| API Endpoint | HTTP | UI Page/Action |
|--------------|------|----------------|
| `GET /api/medicines` | GET | Dashboard summary |
| `GET /api/medicines/pages?page&size` | GET | Medicine List (paginated) |
| `GET /api/medicines/stats` | GET | Dashboard stats cards |
| `GET /api/medicines/expired` | GET | Expired Medicines page |
| `GET /api/medicines/low-stock?stockValue` | GET | Low Stock page |
| `GET /api/medicines/search?name&manufacturer` | GET | Search results |
| `GET /api/medicines/{id}` | GET | Medicine Detail page |
| `POST /api/medicines` | POST | Create form submission |
| `PUT /api/medicines/{id}` | PUT | Edit form submission |
| `PATCH /api/medicines/{id}/increase` | PATCH | Stock increase action |
| `PATCH /api/medicines/{id}/decrease` | PATCH | Stock decrease action |
| `DELETE /api/medicines/{id}` | DELETE | Delete action |

---

## Design Guidelines

### Color Scheme
- **Primary:** Blue (#3B82F6) - Actions, links
- **Success:** Green (#10B981) - OK status, success messages
- **Warning:** Yellow (#F59E0B) - Low stock alerts
- **Danger:** Red (#EF4444) - Expired, errors, delete
- **Neutral:** Gray (#6B7280) - Text, borders

### Typography
- **Headings:** Bold, larger sizes
- **Body:** Regular weight, good line height
- **Monospace:** For IDs and codes

### Spacing
- Consistent padding (p-4, p-6)
- Card margins (m-4)
- Table cell padding (px-4 py-2)

---

## Notes

- All forms use POST with hidden `_method` field for PUT/PATCH/DELETE
- Flash messages passed via RedirectAttributes
- Validation errors bound to model
- Thymeleaf expressions: `th:text`, `th:each`, `th:if`, `th:href`
- Tailwind utility classes for all styling
