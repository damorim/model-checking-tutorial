# About Model Checking

TODO




# JPF

...


### Running a basic sample:

```markdown

Make sure you have docker installed by running:
> $ docker -v

Download the docker image with jpf
> [image](https://drive.google.com/file/d/1sxxM1N3V3E458nmKWflIUhSoC9e4d3BS/view?usp=sharing) 

Load the image from the .tar file you just downloaded
> $ gunzip -c jpf-container.tgz | docker load

Run it
> $ docker run -it jpf

Run JPF with a sample
> $ cd /home/jpf/jpf-core/
> $ java -jar build/RunJPF.jar src/examples/NumericValueCheck.jpf

Other examples can be found in /home/jpf/jpf-core/src/exames
```

# SPF

TODO
