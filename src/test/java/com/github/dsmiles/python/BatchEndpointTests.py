import pytest
import requests
from requests.auth import HTTPBasicAuth

BASE_URL = "https://dev.portal.gpas.world/api/v1"
BATCHES_URL = "/batches"

@pytest.fixture(scope="module", autouse=True)
def setup():
    # Perform setup actions here, e.g., authenticate and obtain JWT token
    username = "your_username"
    password = "your_password"
    auth_response = requests.post(
        f"{BASE_URL}/login",
        auth=HTTPBasicAuth(username, password)
    )
    auth_response.raise_for_status()
    jwt_token = auth_response.json().get("token")
    yield jwt_token
    # Teardown actions can go here if needed

def test_get_batches(setup):
    headers = {
        "Authorization": f"Bearer {setup}"
    }
    response = requests.get(f"{BASE_URL}{BATCHES_URL}", headers=headers)

    assert response.status_code == 200

def test_get_batch_listing(setup):
    headers = {
        "Authorization": f"Bearer {setup}"
    }
    params = {
        "status": "Created",
        "is_approved": False
    }
    response = requests.get(f"{BASE_URL}{BATCHES_URL}", headers=headers, params=params)
    data = response.json()

    assert response.status_code == 200
    assert "batches" in data
    assert isinstance(data["batches"], list)
    assert len(data["batches"]) > 0
    for batch in data["batches"]:
        assert batch["name"] is not None
        assert batch["status"] == "Created"
        assert batch["telemetry_data"] is not None
        assert batch["quality"] == "Pending"
        assert batch["is_approved"] is False
        assert batch["is_shared"] is False
        assert batch["id"] is not None
        assert batch["created_at"] is not None
        assert batch["updated_at"] is not None
        assert batch["uploaded_by"] is not None
        assert batch["sample_count"] is not None
        assert batch["uploader"] is not None
        assert batch["portal_upload_complete"] is False
    assert "total_count" in data
    assert data["total_count"] is not None
