import pytest
import requests
from requests.auth import HTTPBasicAuth

BASE_URL = "https://dev.portal.gpas.world/api/v1"
ORGANISATION_URL = "/admin/organisations"
CREATE_USER_URL = "/admin/create-user"

@pytest.fixture(scope="module")
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

@pytest.mark.parametrize("org_id", ["497f6eca-6276-4993-bfeb-53cbbbba6f08"])
def test_get_admin_info(setup, org_id):
    headers = {"Authorization": f"Bearer {setup}"}
    response = requests.get(f"{BASE_URL}{ORGANISATION_URL}/{org_id}", headers=headers)
    data = response.json()

    assert response.status_code == 200
    assert data["name"] is not None
    assert data["description"] is not None
    assert data["is_sharing"] is not None
    assert data["portal_upload_enabled"] is not None
    assert data["ont_enabled"] is not None
    assert data["has_consented"] is not None
    assert data["id"] == org_id
    assert data["created_at"] == "2019-08-24T14:15:22Z"

def test_create_user(setup):
    user_payload = {
        "name": "string",
        "description": "string",
        "is_sharing": False,
        "portal_upload_enabled": False,
        "ont_enabled": False,
        "has_consented": False
    }
    headers = {
        "Authorization": f"Bearer {setup}",
        "Content-Type": "application/json"
    }
    response = requests.post(f"{BASE_URL}{CREATE_USER_URL}", json=user_payload, headers=headers)
    data = response.json()

    assert response.status_code == 201
    assert data["name"] == "string"
    assert data["description"] == "string"
    assert data["is_sharing"] is False
    assert data["portal_upload_enabled"] is False
    assert data["ont_enabled"] is False
    assert data["has_consented"] is False
    assert data["id"] is not None
