## Welcome to Model Checking Tutorial

### About Model Checking

TODO




# JPF

...

```markdown
Running a basic sample:

Make sure you have docker installed by running:
> $ docker -v

Download the docker image with jpf -> 
> link

Load the image from the .tar file you just downloaded
> $ gunzip -c jpf-container.tgz | docker load

Run it
> $ docker run -it jpf

Run JPF with a sample
> $ cd /home/jpf/jpf-core/
> $ java -jar build/RunJPF.jar src/examples/NumericValueCheck.jpf
```

# SPF

TODO
