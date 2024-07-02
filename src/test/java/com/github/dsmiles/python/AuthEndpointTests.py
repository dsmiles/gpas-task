import pytest
import requests
from requests.auth import HTTPBasicAuth

BASE_URL = "https://dev.portal.gpas.world/api/v1"
AUTH_URL = "/auth/token"

@pytest.fixture(scope="module", autouse=True)
def setup():
    # Perform setup actions here, e.g., setting base URL
    yield
    # Teardown actions can go here if needed

def test_successful_authentication():
    auth_payload = {
        "username": "your_username",
        "password": "your_password"
    }
    headers = {
        "Content-Type": "application/json"
    }
    response = requests.post(f"{BASE_URL}{AUTH_URL}", json=auth_payload, headers=headers)
    data = response.json()

    assert response.status_code == 200
    assert "access_token" in data
    assert "expires_in" in data
    assert "token_type" in data
    assert "scope" in data

def test_authentication_invalid_credentials():
    auth_payload = {
        "username": "invalid_username",
        "password": "invalid_password"
    }
    headers = {
        "Content-Type": "application/json"
    }
    response = requests.post(f"{BASE_URL}{AUTH_URL}", json=auth_payload, headers=headers)
    data = response.json()

    assert response.status_code == 401
    assert data["message"] == "Invalid username or password"

def test_authentication_missing_credentials():
    auth_payload = {
        "username": "your_username"
        # Note: Missing password intentionally to test validation
    }
    headers = {
        "Content-Type": "application/json"
    }
    response = requests.post(f"{BASE_URL}{AUTH_URL}", json=auth_payload, headers=headers)
    data = response.json()

    assert response.status_code == 422
    assert data["detail"][0]["type"] == "missing"
    assert data["detail"][0]["loc"][0] == "body"
    assert data["detail"][0]["loc"][1] == "password"
    assert data["detail"][0]["msg"] == "Field required"
    assert data["detail"][0]["input"]["username"] == "your_username"
