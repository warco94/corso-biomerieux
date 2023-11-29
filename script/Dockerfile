FROM ubuntu:18.04

RUN apt-get update \
    && apt-get install --no-install-recommends --yes coreutils gawk wget git-core diffstat unzip \
    gcc-multilib g++-multilib build-essential chrpath socat cpio python python3 python3-pip python3-pexpect \
    xz-utils debianutils iputils-ping python3-git python3-jinja2 texinfo lsb-release locales file \
    libssl-dev libcrypto++-dev zstd liblz4-tool xxd lzop ifupdown iproute2 sudo iptables \
    && apt-get clean \
    && rm -rf /root/.cache \
    && rm -rf /var/lib/apt/lists/* \
    && locale-gen en_US.UTF-8

RUN groupadd -g 1000 builder \
    && useradd -g 1000 -g sudo -u 1000 -m -s /bin/bash builder

RUN echo "builder:builder" | chpasswd
RUN echo ". /usr/share/bash-completion/completions/git" >> /home/builder/.bashrc

USER builder

ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

WORKDIR /bsp

CMD /bin/bash
