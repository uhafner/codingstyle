#!/bin/bash
# This script generates a new SSH key pair and updates a Docker Compose file with the public key.
# Create secrets directory if it doesn't exist
set -x
LOC=$1
if [[ -z "$LOC" ]]; then
    LOC="./"
fi

echo "Location is $LOC"
ls -artl "$LOC"

rm "$LOC/conductor_ok"

mkdir -p "$LOC/secrets"

# Remove existing keys
rm -fr "$LOC/jenkins_agent_ed" "$LOC/jenkins_agent_ed.pub"

# Generate new ed25519 SSH key pair
ssh-keygen -t ed25519 -f "$LOC/jenkins_agent_ed" -N ""

# Set appropriate permissions for private key
chmod 444 "$LOC/jenkins_agent_ed"

# Extract public key
pubkey=$(cat "$LOC/jenkins_agent_ed.pub")

echo "The public key is $pubkey"

# Update the authorized_keys file with the public key
echo "$pubkey" > "$LOC/authorized_keys" && chown 1000:1000 "$LOC/authorized_keys"

# Generate a random token for JCasc
openssl rand -hex 24 > "$LOC/secrets/jcasc_token"
cat "$LOC/secrets/jcasc_token"

# This file will be used by other containers to know we went up to the end of the key/token generation
echo "OK" > "$LOC/conductor_ok"

# Display success message
echo "SSH key pair generated successfully."
