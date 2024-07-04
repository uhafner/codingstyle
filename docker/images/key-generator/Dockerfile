# Prepare a Debian-based Docker image with several utilities installed to automatically generate SSH keys
FROM debian:bookworm-20240612

# Copy all shell scripts from the current directory to /usr/local/bin/ in the image.
COPY *sh /usr/local/bin/

# Make all shell scripts in /usr/local/bin/ executable.
RUN chmod +x /usr/local/bin/*.sh

# The RUN command executes a series of commands in the new layer of the image and commits the results.
# The following commands are executed:

# 1. Update the package list.
# 2. Install necessary dependencies including several utilities and remove the package list to reduce the image size.
RUN apt update  \
    && apt install -y --no-install-recommends ca-certificates curl git gnupg nano openssh-client procps unzip wget \
    && rm -rf /var/lib/apt/lists/* && rm -fr /tmp/*

# Run the keygen.sh script with /ssh-dir as an argument.
# This script is expected to generate SSH keys and store them in /ssh-dir.
RUN /usr/local/bin/keygen.sh /ssh-dir

# The CMD command specifies the default command to execute when the container starts.
# In this case, it prints a message and lists the contents of /ssh-dir.
CMD ["sh", "-c", "echo 'Export stage is ready'; ls -l /ssh-dir/"]
