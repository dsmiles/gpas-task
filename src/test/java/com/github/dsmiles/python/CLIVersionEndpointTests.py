import pytest
import requests

BASE_URL = "https://dev.portal.gpas.world"
CLI_VERSION_URL = "/cli-version"

@pytest.fixture(scope="module", autouse=True)
def setup():
    # Perform setup actions here, e.g., set base URL
    yield
    # Teardown actions can go here if needed

def test_get_cli_version():
    headers = {
        "Content-Type": "application/json"
    }
    response = requests.get(f"{BASE_URL}{CLI_VERSION_URL}", headers=headers)
    data = response.json()

    assert response.status_code == 200
    assert data["version"] == "1.0.1"
